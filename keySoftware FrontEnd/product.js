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