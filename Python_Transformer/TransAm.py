import math

import numpy as np
import torch
import torch.nn as nn

import GradeGetter
from General_Regression_Training_3d import General_Regression_Training_3d


class PositionalEncoding(nn.Module):

    def __init__(self, d_model, max_len=5000):
        super(PositionalEncoding, self).__init__()

        pe = torch.zeros(max_len, d_model)
        position = torch.arange(0, max_len, dtype=torch.float).unsqueeze(1)
        div_term = torch.exp(torch.arange(0, d_model, 2).float() * (-math.log(10000.0) / d_model))
        pe[:, 0::2] = torch.sin(position * div_term)
        pe[:, 1::2] = torch.cos(position * div_term)
        pe = pe.unsqueeze(0).transpose(0, 1)
        # pe.requires_grad = False
        self.register_buffer('pe', pe)

    def forward(self, x):
        #         print("PositionalEncoding",x.size())

        return x + self.pe[:x.size(0), :]


class TransAm(nn.Module):
    def __init__(self, feature_size=250, num_layers=1, dropout=0.1):
        super(TransAm, self).__init__()
        self.model_type = 'Transformer'

        self.src_mask = None
        self.pos_encoder = PositionalEncoding(feature_size)
        self.encoder_layer = nn.TransformerEncoderLayer(d_model=feature_size, nhead=2, dropout=dropout)
        self.transformer_encoder = nn.TransformerEncoder(self.encoder_layer, num_layers=num_layers)
        self.decoder = nn.Linear(2 * feature_size, 1)
        self.init_weights()

        self.feature_size = feature_size
        self.num_layers = num_layers
        self.dropout = dropout

    def feature(self):
        return {"feature_size": self.feature_size, "num_layers": self.num_layers, "dropout": self.dropout}

    def init_weights(self):
        initrange = 0.1
        self.decoder.bias.data.zero_()
        self.decoder.weight.data.uniform_(-initrange, initrange)

    def forward(self, src):
        #         print("0",src.shape)
        if self.src_mask is None or self.src_mask.size(0) != len(src):
            device = src.device
            mask = self._generate_square_subsequent_mask(len(src)).to(device)
            self.src_mask = mask
        #         print("1",src.shape)
        src = self.pos_encoder(src)
        #         print("2",src.shape)
        output = self.transformer_encoder(src, self.src_mask)  # , self.src_mask)
        output = output.view(output.shape[0], -1)
        #         print("3",output.shape)
        output = self.decoder(output)
        #         print("4",output.shape)
        return output

    def _generate_square_subsequent_mask(self, sz):
        mask = (torch.triu(torch.ones(sz, sz)) == 1).transpose(0, 1)
        mask = mask.float().masked_fill(mask == 0, float('-inf')).masked_fill(mask == 1, float(0.0))
        return mask


def double_X(X_train, X_test):
    # 對特征做一個操作 讓它翻倍以免出現不是雙數的情況
    X_train_Double = []
    for line in X_train:
        tempList = []
        for l in line:
            tempList.extend([l, l])
        X_train_Double.append([np.array(tempList), np.array(tempList)])

    X_train_Double = np.array(X_train_Double)

    X_test_Double = []
    for line in X_test:
        tempList = []
        for l in line:
            tempList.extend([l, l])
        X_test_Double.append([np.array(tempList), np.array(tempList)])

    X_test_Double = np.array(X_test_Double)

    return X_train_Double, X_test_Double


if __name__ == '__main__':
    x_train, x_test, y_train, y_test = GradeGetter.get_grade()
    X_train_Double, X_test_Double = double_X(x_train, x_test)

    print("X_train_Double.shape:", X_train_Double.shape, "X_test_Double.shape:", X_test_Double.shape)

    model = TransAm(feature_size=26, num_layers=1, dropout=0.5)
    grt = General_Regression_Training_3d(model, learning_rate=[1e-3, 1e-6, 1e-8], batch_size=512, use_more_gpu=False,
                                         weight_decay=1e-3, device=0, save_path='transformer_Result', epoch=20000)
    grt.fit(X_train_Double, y_train, X_test_Double, y_test)
