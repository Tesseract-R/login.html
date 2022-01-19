import time

from selenium import webdriver
from selenium.webdriver.common.by import By

# chrome_options = webdriver.ChromeOptions()
# chrome_options.add_argument('--headless')
# chrome_options.add_argument('lang=zh_CN')
# chrome_options.add_argument('user-agent="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36"')
# chrome_options.add_argument('Accept-Encoding="gzip,defale"')
# driver = webdriver.Chrome(options=chrome_options)
driver = webdriver.Chrome()

driver.get('https://www.fanwen99.com/zongjie/')
print(driver.title)
box = driver.find_element(By.CLASS_NAME, 'jjfw_list_t')
contents = box.find_elements(By.XPATH, '//li')
# print("contents: \t", contents)

with open('tmp1.txt', 'w') as file:
    for content in contents:
        print("content: \t", content)
        a = content.find_element(By.XPATH, 'a')
        print("a: \t\t\t", a)
        print(a.get_attribute('href'))
        print(a.get_attribute('title'))
        newdriver = webdriver.Chrome()
        newdriver.get(a.get_attribute('href'))
        article = newdriver.find_element(By.CLASS_NAME, 'content')
        ps = article.find_elements(By.XPATH, 'p')
        for p in ps:
            try:
                print(p.text)
                file.write(p.text)
            except:
                pass
        newdriver.close()
