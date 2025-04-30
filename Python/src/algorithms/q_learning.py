import numpy as np
import random

def q_learning_allocate(df, resources, available, episodes=500):
    n = len(df)
    q_table = np.zeros((n, 2))
    order_ids = df['OrderID'].tolist()

    for ep in range(episodes):
        remaining = available.copy()
        for i in range(n):
            action = random.randint(0, 1) if random.random() < 0.2 else np.argmax(q_table[i])
            need = [df.iloc[i][f'Need_{r}'] for r in resources]
            can_allocate = all(need[j] <= remaining[j] for j in range(len(resources)))
            reward = 10 if df.iloc[i]['Priority'] == 'High' else 5 if df.iloc[i]['Priority'] == 'Medium' else 2
            if action == 1 and can_allocate:
                for j in range(len(resources)):
                    remaining[j] -= need[j]
            elif action == 1:
                reward = -10
            else:
                reward = 0
            next_max = np.max(q_table[i]) if i < n - 1 else 0
            q_table[i, action] += 0.1 * (reward + 0.9 * next_max - q_table[i, action])
    return [order_ids[i] for i in range(n) if np.argmax(q_table[i]) == 1]
