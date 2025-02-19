import csv
with open("logistic_order.csv","w",newline="") as file:
    data=csv.writer(file)
    data.writerow(['OrderID','Warehouse', 'Truck', 'Employee',"Amount","Max_Warehouse","Max_Trucks","Max_Employees","Max_Orders"])

   
   