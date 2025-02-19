import re
diem="toan: 6.0 van: 7.5"
diem=list(map(int,re.findall('(\d+\.\d+)' , diem)))
print(diem)