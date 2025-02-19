import csv
with open("logistic.csv","w") as file:
    data=csv.writer(file)
    data.writerow(['Warehouse', 'Truck', 'Employee',"Amount","Max_Warehouse","Max_Trucks","Max_Employees","Max_Orders"])

    # Ghi dữ liệu vào file CSV
    data.writerow([2, 5, 11,30,3,7,20,40])
    data.writerow([3, 4, 10,30,5,6,30,50])
    data.writerow([2, 2, 5,25,3,3,10,30])
    data.writerow([1, 2, 3,20,2,3,4,25])
    data.writerow([1, 3, 2,50,2,4,4,100])
    data.writerow([3, 1, 9,100,5,3,10,120])
    data.writerow([2, 2, 10,60,3,3,15,70])
   