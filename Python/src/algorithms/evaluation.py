def evaluate(df, allocated_ids, available, total, runtime_ms):
    df_alloc = df[df['OrderID'].isin(allocated_ids)].copy()
    score_map = {'High': 3, 'Medium': 2, 'Low': 1}
    df_alloc['PriorityScore'] = df_alloc['Priority'].map(score_map)

    return {
        'Orders Served': len(df_alloc),
        'Fulfillment Rate (%)': round(100 * len(df_alloc) / len(df), 2),
        'Avg Processing Time': round(df_alloc['ProcessingTime(min)'].mean(), 2),
        'Avg Priority Score': round(df_alloc['PriorityScore'].mean(), 2),
        'Used Resource (%)': round(100 * (1 - sum(available) / sum(total)), 2),
        'Runtime (ms)': round(runtime_ms, 2)
    }
