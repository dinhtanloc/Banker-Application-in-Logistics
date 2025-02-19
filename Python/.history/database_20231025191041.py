import csv
with open("test.csv","w",newline="") as file:
    data=csv.writer(file)
    data.writerow(['OrderID','Warehouse', 'Truck', 'Employee',"Amount","Max_Warehouse","Max_Trucks","Max_Employees","Max_Orders"])
    data.writerows([[i for i in range(1,8)],[i for i in range(1,8)],[i for i in range(15,22,-1)]])

   
   