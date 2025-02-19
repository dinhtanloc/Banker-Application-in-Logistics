import csv
import random as rd
data_count=500
col1=[a for a in range(1,data_count+1)]
col2=[rd.choice(list(range(5))) for _ in data_count]
col3=[rd.choice(list(range(20))) for _ in data_count]
col4=[rd.choice(list(range(50))) for _ in data_count]
col5=[rd.choice(list(range(100))) for _ in data_count]
col6=[i+rd.choice(list(range(3))) for _ in data_count]
col7=[i+ rd.choice(list(range(5))) for i in col3]
col8=[i+rd.choice(list(range(20))) for i in col4]
col9=[i+rd.choice(list(range(50))) for i in col5]
with open("test.csv","w",newline="") as file:
    data=csv.writer(file)
    data.writerow(['OrderID','Warehouse', 'Truck', 'Employee',"Amount","Max_Warehouse","Max_Trucks","Max_Employees","Max_Orders"])
    for i in range(1,data_count+1):
        data.writerow([col1[i-1],col2[i-1],col3[i-1],col4[i-1],col5[i-1],col6[i-1],col7[i-1],col8[i-1],col9[i-1]])
   
   