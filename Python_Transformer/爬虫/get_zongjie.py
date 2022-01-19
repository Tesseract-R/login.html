import time

from selenium import webdriver
from selenium.webdriver.common.by import By

# html_uri = ['xuexizongjie', 'gongzuozongjie', 'nianzhongzongjie', 'shixizongjie', 'huodongzongjie','junxunzongjie', 'zongjiebaogao', 'gongzuojiehua']


for uri in html_uri:
    driver = webdriver.Chrome()
    driver.get('https://www.dazongjie.com/{}'.format(uri))
    print(driver.title)
    main = driver.find_element(By.XPATH, '//div[@class="main-left"]')
    box = main.find_element(By.XPATH, '//div[@class="zuowen_box"]')
    print(box)
    contents = box.find_elements(By.XPATH, '//li')
    # print("contents: \t", contents)

    with open('tmp_{}.txt'.format(uri), 'w') as file:
        for content in contents:
            print("content: \t", content)
            a = content.find_element(By.XPATH, 'a')
            print("a: \t\t\t", a)
            print(a.get_attribute('href'))
            print(a.get_attribute('title'))
            if a.get_attribute('href').endswith(".html"):
                newdriver = webdriver.Chrome()
                newdriver.get(a.get_attribute('href'))
                article = newdriver.find_element(By.CLASS_NAME, 'content')
                ps = article.find_elements(By.XPATH, 'p')
                for p in ps:
                    try:
                        # print(p.text)
                        file.write(p.text)
                    except:
                        pass
                newdriver.close()
    driver.close()
