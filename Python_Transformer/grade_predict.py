# -*- coding: utf-8 -*-
"""
Created on Mon Jan 11 10:27:35 2021
"""

import GradeGetter
from matplotlib import pyplot as plt
from sklearn.preprocessing import MinMaxScaler
from sklearn.svm import SVR
from sklearn.model_selection import GridSearchCV
from sklearn.metrics import mean_squared_error  # 批量导入指标算法
import numpy as np


def grade_predict(train_x, test_x, train_y, test_y):
    # 特征归一化
    train_y_normal = train_y

    mm = MinMaxScaler()
    train_x_normal = mm.fit_transform(train_x)
    test_x_normal = mm.transform(test_x)

    # 设置超参数
    C = [0.1, 0.2, 0.5, 0.8, 0.9, 1, 2, 5, 10, 100, 1000]
    kernel = 'rbf'
    gamma = [0.001, 0.01, 0.1, 0.2, 0.5, 0.8]
    epsilon = [0.01, 0.05, 0.1, 0.2, 0.5, 0.8, 1, 10, 100]
    # 参数字典
    params_dict = {
        'C': C,
        'gamma': gamma,
        'epsilon': epsilon
    }

    # 初始化支持向量机
    gsCV = GridSearchCV(SVR(kernel='rbf', gamma=100), cv=10, param_grid=params_dict)
    gsCV.fit(train_x_normal, train_y_normal)
    svr = SVR(C=gsCV.best_params_['C'], kernel=kernel, gamma=gsCV.best_params_['gamma'],
              epsilon=gsCV.best_params_['epsilon'])
    svr.fit(train_x_normal, train_y_normal)

    # 用测试集检验误差
    y_svr = svr.predict(test_x_normal)
    mse = mean_squared_error(test_y, y_svr)
    return svr, mm, mse, test_y, y_svr


def update_system():
    while(True):
        svr, mm, mse, test_y, y_svr = grade_predict(GradeGetter.get_grade())
        print(mse)
        # if mse <= 100:
            # 保存模型
        # joblib.dump(svr, r'svm.joblib')
        # joblib.dump(mm, r'mm')
        return test_y, y_svr, mse
            # return True

def get_predict_rank(train_x, test_x, train_y, test_y):
    svr, mm, mse, test_y, y_svr = grade_predict(train_x, test_x, train_y, test_y)
    print("SVM MSE: ", mse)
    return rank(y_svr)


def rank(lis):
    copylis = lis[:]
    copylis = sorted(copylis, reverse=True)
    dic = {v: i + 1 for i, v in enumerate(copylis)}
    return [dic[v] for i, v in enumerate(lis)]


if __name__ == '__main__':
    test_y, y_svr, mse = update_system()
    print("预测偏差：", mse)
    rank_svr = rank(y_svr)
    rank_test = rank(test_y)
    print(rank_svr)
    print(rank_test)
    
    # 在图上可视化
    X = np.linspace(1,len(y_svr),len(y_svr))
    plt.scatter(X, test_y.values, c='k', label="Final Score", zorder=1)
    plt.scatter(X, y_svr, c='r', label="Predict Score (MSE: %.3f)" % mse)
    plt.xlabel('Student')
    plt.ylabel('Score')
    plt.legend()
    plt.figure()
    plt.show()

