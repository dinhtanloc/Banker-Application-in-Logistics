from deap import base, creator, tools, algorithms
import random

def ga_optimize(df_orders, resources, available, ngen=40, pop_size=100):
    num_orders = len(df_orders)

    def evaluate(individual):
        remain = available.copy()
        score = 0
        for idx, assign in enumerate(individual):
            if assign == 1:
                need = [df_orders.iloc[idx][f'Need_{res}'] for res in resources]
                if all(need[i] <= remain[i] for i in range(len(resources))):
                    for i in range(len(resources)):
                        remain[i] -= need[i]
                    prio_score = {'High': 3, 'Medium': 2, 'Low': 1}[df_orders.iloc[idx]['Priority']]
                    score += prio_score * 10 - df_orders.iloc[idx]['ProcessingTime(min)']/10
                else:
                    score -= 5
        return (score,)

    creator.create("FitnessMax", base.Fitness, weights=(1.0,))
    creator.create("Individual", list, fitness=creator.FitnessMax)

    toolbox = base.Toolbox()
    toolbox.register("attr_bool", lambda: random.randint(0, 1))
    toolbox.register("individual", tools.initRepeat, creator.Individual, toolbox.attr_bool, n=num_orders)
    toolbox.register("population", tools.initRepeat, list, toolbox.individual)

    toolbox.register("evaluate", evaluate)
    toolbox.register("mate", tools.cxTwoPoint)
    toolbox.register("mutate", tools.mutFlipBit, indpb=0.1)
    toolbox.register("select", tools.selTournament, tournsize=3)

    pop = toolbox.population(n=pop_size)
    algorithms.eaSimple(pop, toolbox, cxpb=0.5, mutpb=0.2, ngen=ngen, verbose=False)

    best = tools.selBest(pop, k=1)[0]
    selected_orders = [df_orders.iloc[i]['OrderID'] for i, bit in enumerate(best) if bit == 1]
    return selected_orders
