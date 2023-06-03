

fetch('http://localhost:8080/product/deleted/')
    .then(response => response.json())
    .then(data => {
        const productContainer = document.getElementById('deletedProductContainer');
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
                <button onclick="undoProduct(${product.productId})">Restore</button>
            `;

            productContainer.appendChild(productElement);
        });
    });

    function undoProduct(productId) {
        fetch(`http://localhost:8080/product/undo?id=${productId}`, {
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
