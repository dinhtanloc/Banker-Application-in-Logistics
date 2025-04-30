import numpy as np
import pandas as pd
import random

resources = ['Trucks', 'Employees', 'Forklifts']
num_resources = len(resources)
num_orders = 20  # Số lượng đơn hàng (processes)

# Tổng tài nguyên toàn hệ thống (Available + Allocation)
total_resources = np.random.randint(low=10, high=20, size=num_resources)

# Ma trận Available: số tài nguyên hiện còn chưa cấp phát
Available = np.random.randint(low=5, high=10, size=num_resources)

# Khởi tạo ma trận Max (yêu cầu tối đa cho mỗi đơn hàng)
Max = np.zeros((num_orders, num_resources), dtype=int)
Allocation = np.zeros((num_orders, num_resources), dtype=int)

for i in range(num_orders):
    for j in range(num_resources):
        Max[i, j] = np.random.randint(1, total_resources[j] // 2 + 1)
        Allocation[i, j] = np.random.randint(0, Max[i, j] + 1)

# Tính ma trận Need
Need = Max - Allocation

# Tạo thông tin đơn hàng bổ sung
order_ids = [f'ORDER_{i+1:03d}' for i in range(num_orders)]
priorities = np.random.choice(['High', 'Medium', 'Low'], size=num_orders, p=[0.2, 0.5, 0.3])
processing_time = np.random.normal(loc=120, scale=30, size=num_orders).astype(int)  # phút

# Tạo DataFrame mô tả đơn hàng
df_orders = pd.DataFrame({
    'OrderID': order_ids,
    'Priority': priorities,
    'ProcessingTime(min)': processing_time
})

# Gắn ma trận Max, Allocation, Need vào từng đơn hàng
for i, res in enumerate(resources):
    df_orders[f'Max_{res}'] = Max[:, i]
    df_orders[f'Alloc_{res}'] = Allocation[:, i]
    df_orders[f'Need_{res}'] = Need[:, i]

# Hiển thị Available
available_df = pd.DataFrame({
    'Resource': resources,
    'Available': Available,
    'Total_System': total_resources
})

# Xuất file nếu cần
df_orders.to_csv("../../data/banker_orders_dataset.csv", index=False)
available_df.to_csv("../../data/banker_available_resources.csv", index=False)

# In mẫu dữ liệu
print("🔹 Available resources:")
print(available_df)
print("\n🔹 Sample order data:")
print(df_orders.head())
