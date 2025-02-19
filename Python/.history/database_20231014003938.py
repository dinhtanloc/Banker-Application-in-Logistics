import csv
with open("logistic","w") as file:
    data=csv.writer(file)
    data.writerow(['Warehouse', 'Truck', 'Employee',"Amount"])

    # Ghi dữ liệu vào file CSV
    data.writerow([2, 5, 11,30])
    data.writerow([3, 4, 10,30])
    data.writerow([2, 2, 5,25])
    data.writerow([1, 2, 3,20])
    data.writerow([1, 3, 2,50])
    data.writerow([3, 1, 9,100])
    data.writerow([2, 2, 10,60])
   