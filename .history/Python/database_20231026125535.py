import csv
import random as rd
data_count=7
col1=[a for a in range(1,8)]
col2=[2,3,4,5,2,3,4]
col3=[5,6,3,4,5,7,9]
col4=[20,18,15,23,22,4,3]
col5=[60,50,30,24,28,46,50]
col6=[5,4,5,7,5,4,6]
col7=[i+ rd.choice(list(range(5))) for i in col3]
col8=[i+rd.choice(list(range(20))) for i in col4]
col9=[i+rd.choice(list(range(50))) for i in col5]
with open("test.csv","w",newline="") as file:
    data=csv.writer(file)
    data.writerow(['OrderID','Warehouse', 'Truck', 'Employee',"Amount","Max_Warehouse","Max_Trucks","Max_Employees","Max_Orders"])
    for i in range(1,data_count+1):
        data.writerow([col1[i-1],col2[i-1],col3[i-1],col4[i-1],col5[i-1],col6[i-1],col7[i-1],col8[i-1],col9[i-1]])
   
   