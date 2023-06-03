// Fetch all categories and display them
fetch('http://localhost:8080/category/')
    .then(response => response.json())
    .then(data => {
        const categoryContainer = document.getElementById('categoryContainer');
        categoryContainer.innerHTML = '';

        data.forEach(category => {
            console.log(category);
            const categoryElement = document.createElement('div');
            categoryElement.classList.add('category-item');
            categoryElement.innerHTML = `
                <h3>${category.categoryName}</h3>
                <p>${category.categoryDescription}</p>
                <button onclick="deleteCategory(${category.categoryId})">Delete</button>
            `;

            categoryContainer.appendChild(categoryElement);
        });
    })
    .catch(error => {
        console.error(error);
    });

// Function to delete a category
function deleteCategory(categoryId) {
    fetch(`http://localhost:8080/category/delete?id=${categoryId}`, {
        method: 'DELETE'
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            location.reload(); // Refresh the page
        })
        .catch(error => {
            console.error(error);
        });
}


fetch('http://localhost:8080/product/')
    .then(response => response.json())
    .then(data => {
        const productContainer = document.getElementById('productContainer');
        productContainer.innerHTML = '';

        data.forEach(product => {
            console.log(product);
            const productElement = document.createElement('div');
            productElement.classList.add('product-item');
            productElement.innerHTML = `
                <div class="product-image">
                    <img src="${product.productImageUrl}" alt="${product.productName}">
                </div>
                <h3>${product.productName}</h3>
                <p>${product.productDescription}</p>
                <p>${product.productPrice}</p>
                <p>${product.productBrand}</p>
            `;

            productContainer.appendChild(productElement);
        });
    });


    // Handle form submission
document.getElementById('categoryForm').addEventListener('submit', function (event) {
    event.preventDefault(); // Prevent the default form submission

    const form = event.target;
    const categoryName = form.elements.categoryName.value;
    const categoryDescription = form.elements.categoryDescription.value;

    const category = {
        categoryName: categoryName,
        categoryDescription: categoryDescription
    };

    // Make a POST request to add the category
    fetch('http://localhost:8080/category/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(category)
    })
        .then(response => response.json())
        .then(data => {
            // Handle the response data as needed
            console.log(data);
            location.reload();
        })
        .catch(error => {
            // Handle any error that occurred during the request
            console.error(error);
        });
});

// Fetch all categories and populate the selector
fetch('http://localhost:8080/category/')
    .then(response => response.json())
    .then(data => {
        const categorySelector = document.getElementById('categorySelector');

        data.forEach(category => {
            const option = document.createElement('option');
            option.value = category.categoryId;
            option.textContent = category.categoryName;
            categorySelector.appendChild(option);
        });
    })
    .catch(error => {
        console.error(error);
    });

// Handle form submission
document.getElementById('productForm').addEventListener('submit', function (event) {
    event.preventDefault(); // Prevent the default form submission

    const form = event.target;
    const productName = form.elements.productName.value;
    const productDescription = form.elements.productDescription.value;
    const productPrice = form.elements.productPrice.value;
    const productBrand = form.elements.productBrand.value;
    const productImageUrl = form.elements.productImageUrl.value;
    const productAvailableQuantity = form.elements.productAvailableQuantity.value;
    const productLengthInCm = form.elements.productLengthInCm.value;
    const productBreadthInCm = form.elements.productBreadthInCm.value;
    const productHeightInCm = form.elements.productHeightInCm.value;
    const productColor = form.elements.productColor.value;
    const categoryId = form.elements.categoryId.value; 
    // Extract other product properties from the form as needed

    const product = {
        productName: productName,
        productDescription: productDescription,
        productPrice: productPrice,
        productBrand: productBrand,
        productImageUrl: productImageUrl,
        productAvailableQuantity: productAvailableQuantity,
        productLengthInCm: productLengthInCm,
        productBreadthInCm: productBreadthInCm,
        productHeightInCm: productHeightInCm,
        productColor: productColor,
        categoryId: categoryId
        // Assign other product properties extracted from the form
    };

    // Make a POST request to add the product
    fetch(`http://localhost:8080/product/add?id=${categoryId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(product)
    })
        .then(response => response.json())
        .then(data => {
            // Handle the response data as needed
            console.log(data);
            // Refresh the page
            location.reload();
        })
        .catch(error => {
            // Handle any error that occurred during the request
            console.error(error);
        });
});

