file = open('raw_data.txt', 'r', encoding='utf-8')

with open('tmp_json.txt', 'w') as json_file:
    for line in file.readlines():
        json_file.write("\"{}\",".format(line))
