package com.aubrey.recepku.data.model.recommended

object RecommendedRecipeData {
    val recommended_recipes = listOf(
        RecommendedRecipe(
            id = 1,
            title = "Burger",
            description = "Burger is a sandwich consisting of one or more cooked patties of ground meat, usually beef, placed inside a sliced bread roll or bun.",
            photoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0b/RedDot_Burger.jpg/800px-RedDot_Burger.jpg",
            ingredients = listOf("Bread", "Meat", "Vegetables"),
            steps = listOf("Cook meat", "Put meat in bread", "Add vegetables"),
            healthyIngredients = listOf("Bread", "Vegetables"),
            healthySteps = listOf("Put vegetables in bread")
        ),
        RecommendedRecipe(
            id = 2,
            title = "Pizza",
            description = "Pizza is a savory dish of Italian origin consisting of a usually round, flattened base of leavened wheat-based dough topped with tomatoes, cheese, and often various other ingredients",
            photoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/Eq_it-na_pizza-margherita_sep2005_sml.jpg/800px-Eq_it-na_pizza-margherita_sep2005_sml.jpg",
            ingredients = listOf("Bread", "Tomatoes", "Cheese"),
            steps = listOf("Cook bread", "Put tomatoes and cheese on bread"),
            healthyIngredients = listOf("Bread", "Tomatoes"),
            healthySteps = listOf("Put tomatoes on bread")
        ),
        RecommendedRecipe(
            id = 3,
            title = "Spaghetti",
            description = "Spaghetti is a long, thin, solid, cylindrical pasta. It is a staple food of traditional Italian cuisine.",
            photoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/66/Spaghetti_served_with_tomato_sauce%2C_beans_and_salad.jpg/800px-Spaghetti_served_with_tomato_sauce%2C_beans_and_salad.jpg",
            ingredients = listOf("Pasta", "Tomatoes", "Meat"),
            steps = listOf("Cook bread", "Put tomatoes and cheese on bread"),
            healthyIngredients = listOf("Bread", "Tomatoes"),
            healthySteps = listOf("Put tomatoes on bread")
        ),
        RecommendedRecipe(
            id = 4,
            title = "Sushi",
            description = "Sushi is a traditional Japanese dish of prepared vinegared rice, usually with some sugar and salt, accompanying a variety of ingredients, such as seafood, often raw, and vegetables.",
            photoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/60/Sushi_platter.jpg/800px-Sushi_platter.jpg",
            ingredients = listOf("Rice", "Seafood", "Vegetables"),
            steps = listOf("Cook bread", "Put tomatoes and cheese on bread"),
            healthyIngredients = listOf("Bread", "Tomatoes"),
            healthySteps = listOf("Put tomatoes on bread")
        ),
        RecommendedRecipe(
            id = 5,
            title = "Steak",
            description = "A steak is a meat",
            photoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2b/20150606_155440_-_Flickr_-_Jeffrey_Zeldman.jpg/800px-20150606_155440_-_Flickr_-_Jeffrey_Zeldman.jpg",
            ingredients = listOf("Meat"),
            steps = listOf("Cook bread", "Put tomatoes and cheese on bread"),
            healthyIngredients = listOf("Bread", "Tomatoes"),
            healthySteps = listOf("Put tomatoes on bread")
        ),
        RecommendedRecipe(
            id = 6,
            title = "Salad",
            description = "Salad is a dish consisting of pieces of food in a mixture, with at least one raw ingredient.",
            photoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/94/Salad_platter.jpg/800px-Salad_platter.jpg",
            ingredients = listOf("Vegetables"),
            steps = listOf("Cook bread", "Put tomatoes and cheese on bread"),
            healthyIngredients = listOf("Bread", "Tomatoes"),
            healthySteps = listOf("Put tomatoes on bread")
        ),
RecommendedRecipe(
            id = 7,
            title = "Pancake",
            description = "A pancake is a flat cake, often thin and round, prepared from a starch-based batter that may contain eggs, milk and butter and cooked on a hot surface such as a griddle or frying pan, often frying with oil or butter.",
            photoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/43/Blueberry_pancakes_%282%29.jpg/800px-Blueberry_pancakes_%282%29.jpg",
            ingredients = listOf("Flour", "Eggs", "Milk"),
    steps = listOf("Cook bread", "Put tomatoes and cheese on bread"),
    healthyIngredients = listOf("Bread", "Tomatoes"),
    healthySteps = listOf("Put tomatoes on bread")
        ),
        RecommendedRecipe(
            id = 8,
            title = "Donut",
            description = "A doughnut or donut is a type of fried dough confection or dessert food.",
            photoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/Glazed-Donut.jpg/800px-Glazed-Donut.jpg",
            ingredients = listOf("Flour", "Eggs", "Milk"),
            steps = listOf("Cook bread", "Put tomatoes and cheese on bread"),
            healthyIngredients = listOf("Bread", "Tomatoes"),
            healthySteps = listOf("Put tomatoes on bread")
        ),
    )
}