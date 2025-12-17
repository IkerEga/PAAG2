// Please see documentation at https://learn.microsoft.com/aspnet/core/client-side/bundling-and-minification
// for details on configuring this project to bundle and minify static web assets.

// Write your JavaScript code.

/*Saskira gehitzeko*/
$(document).on("click", ".AddLink", function (e) {
    e.preventDefault();
    var recordToAdd = $(this).attr("data-id");
    if (recordToAdd !== '') {
        $.post("/Saskia/SaskiaGehituAjax", { id: recordToAdd }, function (data) {
            // Produktuaren kantitatea
            $("#item-count-" + recordToAdd).text(data.kantitatea);
            // Subtotala
            $("#item-subtotal-" + recordToAdd).text(
                data.lineSubtotal.toLocaleString('eu-ES', { style: 'currency', currency: 'EUR' })
            );
            // Saskia guztira
            $("#cart-total").text(
                data.guztira.toLocaleString('eu-ES', { style: 'currency', currency: 'EUR' })
            );
            $("#update-message").text(data.mezua);
        });
    }
});