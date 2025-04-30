def banker_allocate(df, resources, available):
    allocated = []
    available = available.copy()
    for _, row in df.iterrows():
        need = [row[f'Need_{r}'] for r in resources]
        if all(need[i] <= available[i] for i in range(len(resources))):
            allocated.append(row['OrderID'])
            for i in range(len(resources)):
                available[i] -= need[i]
    return allocated, available
