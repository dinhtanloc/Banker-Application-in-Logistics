import pandas as pd, time
from algorithms.greedy import greedy_allocate
from algorithms.banker import banker_allocate
from algorithms.q_learning import q_learning_allocate
from algorithms.genetic import ga_optimize
from algorithms.evaluation import evaluate

resources = ['Trucks', 'Employees', 'Forklifts']
df = pd.read_csv('logistics_optimizer/data/synthetic_orders.csv')
res_df = pd.read_csv('logistics_optimizer/data/resources.csv')
available = res_df['Available'].to_numpy()
total = res_df['Total'].to_numpy()

results = []

# Greedy
start = time.time()
greedy, avail_g = greedy_allocate(df.copy(), resources, available.copy())
end = time.time()
results.append({'Method': 'Greedy', **evaluate(df, greedy, avail_g, total, (end - start) * 1000)})

# Q-Learning
start = time.time()
q = q_learning_allocate(df.copy(), resources, available.copy())
end = time.time()
results.append({'Method': 'Q-Learning', **evaluate(df, q, available.copy(), total, (end - start) * 1000)})

# Banker
start = time.time()
banker, avail_b = banker_allocate(df.copy(), resources, available.copy())
end = time.time()
results.append({'Method': 'Banker', **evaluate(df, banker, avail_b, total, (end - start) * 1000)})


start = time.time()
alloc_ga = ga_optimize(df.copy(), resources, available.copy())
end = time.time()
results.append({'Method': 'Genetic Algorithm', **evaluate(df, alloc_ga, available.copy(), total, (end-start)*1000)})

pd.DataFrame(results).to_csv("logistics_optimizer/optimizer_comparison.csv", index=False)
print(pd.DataFrame(results))
