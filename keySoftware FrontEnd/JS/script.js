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
                <div class="product-details">
                    <h3>${product.productName}</h3>
                    <p>${product.productDescription}</p>
                    <p>Brand: ${product.productBrand}</p>
                    <p>price: ${product.productPrice}</p>
                    <p>Quantity: ${product.productAvailableQuantity}</p>
                    <p>Dimentions in cm: ${product.productLengthInCm} X ${product.productBreadthInCm} X ${product.productHeightInCm}</p>
                </div>
                <div id="twoButtons">
                    <button onclick="deleteProduct(${product.productId})">Delete</button>
                    <button onclick="openUpdatePage(${product.productId})" id="updateButton">update</button>
                </div>
            `;

            productContainer.appendChild(productElement);
        });
    });

    function openUpdatePage(productId) {
        // Open the update page in a new window/tab
        const updatePage = window.open('update.html', '_blank');
        
        // Fetch the product details based on the productId
        fetch(`http://localhost:8080/product/${productId}`)
          .then(response => response.json())
          .then(product => {
            // Pass the product details to the update page
            updatePage.productDetails = product;
          })
          .catch(error => {
            console.error(error);
          });
      }
    


// Function to delete a product
function deleteProduct(productId) {
    fetch(`http://localhost:8080/product/delete?id=${productId}`, {
        method: 'PUT'
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
