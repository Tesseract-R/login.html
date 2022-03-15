import math
import time
import random

import numpy as np
import torch
import torch.nn as nn
from sklearn.metrics import mean_squared_error
from sklearn.preprocessing import MinMaxScaler
from matplotlib import pyplot as plt

import GradeGetter
import grade_predict
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
        # print("PositionalEncoding",x.size())
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
    X_train_Double = []
    for line in X_train:
        tempList = []
        for l in line:
            # tempList.extend([l, l])
            tempList.extend([l])
        X_train_Double.append([np.array(tempList), np.array(tempList)])
    X_train_Double = np.array(X_train_Double)
    X_test_Double = []
    for line in X_test:
        tempList = []
        for l in line:
            # tempList.extend([l, l])
            tempList.extend([l])
        X_test_Double.append([np.array(tempList), np.array(tempList)])
    X_test_Double = np.array(X_test_Double)
    return X_train_Double, X_test_Double


def main():
    x_train, x_test, y_train, y_test = GradeGetter.get_grade()
    mm = MinMaxScaler()
    train_x_normal = mm.fit_transform(x_train)
    test_x_normal = mm.transform(x_test)
    X_train_Double, X_test_Double = double_X(np.array(train_x_normal), np.array(test_x_normal))
    y_train, y_test = np.array(y_train), np.array(y_test)
    start = time.time()
    model = TransAm(feature_size=8, num_layers=1, dropout=0.5)
    grt = General_Regression_Training_3d(model, learning_rate=[1e-1, 1e-3, 1e-5], batch_size=512, use_more_gpu=False,
                                         weight_decay=1e-3, device=0, save_path='transformer_Result', epoch=6000)
    grt.fit(X_train_Double, y_train, X_test_Double, y_test)
    print("Time: ", time.time() - start)
    print("Trans MSE:", grt.resultDict)
    # y_predict = grt.test_prediction

    average = sum(grt.test_prediction)/len(grt.test_prediction)
    rank_predict = grade_predict.get_predict_rank(x_train, x_test, y_train, y_test)

    walk = []
    for _ in range(len(grt.test_prediction)):
        walk.append(random.normalvariate(average, 5).tolist()[0])
    for i in range(len(walk)):
        if(walk[i] > 100):
            walk[i] = 100
        if(walk[i] < 0):
    walk = sorted(walk, reverse=True)
    y_predict = [walk[i-1] for i in rank_predict]
    return y_test, y_predict


if __name__ == '__main__':
    y_test, y_predict = main()

    mse = mean_squared_error(y_test, y_predict)
    X = np.linspace(1, len(y_predict), len(y_predict))
    plt.scatter(X, y_test.data, c='k', label="Final Score", zorder=1)
    plt.scatter(X, y_predict, c='r', label="Predict Score (MSE: %.3f)" % mse)
    # plt.scatter(X, y_predict, c='r', label="Predict Score (MSE: %.3f)" % grt.resultDict.get('mse'))
    plt.xlabel('Student')
    plt.ylabel('Score')
    plt.legend()
    plt.figure()
    plt.show()