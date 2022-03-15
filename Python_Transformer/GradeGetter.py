
import pymysql
import pandas as pd
from sklearn.model_selection import train_test_split


def get_grade():
    # 连接数据库
    db = pymysql.connect(host='localhost', user='root', password='california', database='seconddegree', charset='utf8')
    cursor = db.cursor()
    cursor2 = db.cursor()
    cursor3 = db.cursor()

    sql = "SELECT student_id,final_score,Semester_start_score FROM final_score"
    list1 = []
    try:
        cursor.execute(sql)
        results = cursor.fetchall()
        for row in results:
            f_id = row[0]
            f_final = row[1]
            f_start = row[2]
            sql2 = "SELECT i.Structured_design, i.Software_process,i.Detailed_design,i.Demand_analysis,i.Tests_for_realization,i.Maintenance,v.viewing_time_length from inclass_test i, viewing_time_ranking v where i.student_id = v.student_id and i.student_id = \"" + f_id + "\""

            try:
                cursor2.execute(sql2)
                results2 = cursor2.fetchone()
                fscore0 = results2[0]
                fscore1 = results2[1]
                fscore2 = results2[2]
                fscore3 = results2[3]
                fscore4 = results2[4]
                fscore5 = results2[5]
                viewtime = results2[6]
                list1.append({"student_id": f_id, "Semester_start_score": f_start, "Structured_design": fscore0,
                              "Software_process": fscore1,
                              "Detailed_design": fscore2, "Demand_analysis": fscore3, "Tests_for_realization": fscore4,
                              "Maintenance": fscore5, "final_score": f_final, "viewing_time_length": viewtime})
            except:
                print("Error, unable to fetch data")
    except:
        print("Error, unable to fetch data")

    db.close()
    df = pd.DataFrame(list1,
                      columns=['Semester_start_score', 'Structured_design', 'Software_process', 'Detailed_design',
                               'Demand_analysis', 'Tests_for_realization', 'Maintenance',
                               'viewing_time_length', 'final_score',])
    df.to_csv(r"E:\Study\SSPKU\软件工程\小组课题\在线行为数据和期末成绩\score_all.csv", index=False, header=None)  # 保存数据
    df = pd.read_csv(r"E:\Study\SSPKU\软件工程\小组课题\在线行为数据和期末成绩\score_all.csv", header=None)
    # 分割训练、测试集 （期末成绩为y值，其他数据为x值）
    print("Splitting training data and testing data...")
    # print(df)
    x = df.iloc[:,0:8]
    y = df.iloc[:,8]
    train_x, test_x, train_y, test_y = train_test_split(x, y, test_size=0.1)
    return train_x, test_x, train_y, test_y
