import re
diem="toan: 6.0 van: 7.5"
diem=list(map(lambda x:float(x),re.findall('(\d+\.\d+)' , diem)))
print(diem)