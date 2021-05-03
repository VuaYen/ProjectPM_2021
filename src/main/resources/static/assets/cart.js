jQuery(document).ready(function ($) {
    'use strict';
    $('.btn-cart').on('click', function (evt) {
        evt.preventDefault()
        let jLink = $(evt.currentTarget);
        let productId = parseInt(jLink.attr('data-product-id'))
        let qty = 1
        let qtyInput = $('#item-qty-' + productId.toString())
        if (qtyInput.length === 1) {
            qty = parseInt(qtyInput.val())
        }
        addToCart(productId, qty)
    })

    $('.btn-remove-from-cart').on('click', function (evt) {
        evt.preventDefault()
        evt.stopPropagation()

        let jBtn = $(evt.currentTarget)
        let productId = parseInt(jBtn.attr('data-product-id'))
        removeFromCart(productId)
    })

    $('.cart-item-spinner-qty').on('change', function (evt) {
        evt.preventDefault()
        evt.stopPropagation()

        let jInput = $(evt.currentTarget)
        let productId = parseInt(jInput.attr('data-product-id'))
        let qty = jInput.val()
        console.log(qty)
        adjustQty(productId, qty)

    })

    $('#btn-use-points').on('click', function (evt) {
        let points = parseInt($('#input-points').val())
        $('#cell-points').html(points)
        $('#hidden-points').val(points)
        $('#point-modal').modal('hide')
    })

    $('#checkout-diff-address').on('change', function () {
        let theChk = $('#checkout-diff-address')[0];
        let display = theChk.checked ? 'block' : 'none';
        if (!theChk.checked) {
            $('#order-shippingAddress').val('')
        }
        $('.shipping-address').css('display', display);
    });

})

adjustQty = function (productId, qty) {
    let url = '/cart-adjust-qty/' + productId.toString() + '/' + qty.toString()
    $.ajax({
        url: url,
        type: 'GET',
        contentType: 'application/json'
    }).done(function (result) {
        if (result) {
            let order = result.order
            let productId = parseInt(result.productId);
            let sellerSection = $('#seller-section-' + order.seller.username);
            sellerSection.find('.tax').html(order.tax.toFixed(2))
            sellerSection.find('.total').html(order.total.toFixed(2))
            sellerSection.find('.sub-total').html((order.total - order.tax - order.shippingFee).toFixed(2))

            let newItemTotal = 0;
            $.each(result.order.orderDetailList, function (index, item) {
                if (productId === item.product.id) {
                    newItemTotal = item.qty * item.sellPrice;
                }
            })

            $('#cart-item-' + productId.toString()).find('.total-col').html('$' + (newItemTotal.toFixed(2)).toString())

            $('#cart-count').html(result.cartItemTotal)
        }
    })
        .fail(function (error) {
            console.log(error)
        })
}

addToCart = function (productId, qty) {
    if (!qty) {
        qty = 1
    }

    let url = '/add-to-cart/' + productId.toString() + "/" + qty.toString()
    $.ajax({
        url: url,
        type: 'GET',
        contentType: 'application/json'
    }).done(function (result) {
        if (result !== -1) {
            $('#cart-count').html(result)
        }
    })
        .fail(function (error) {
            console.log(error)
        })
}

removeFromCart = function (productId) {
    let url = '/remove-from-cart/' + productId.toString()
    $.ajax({
        url: url,
        type: 'GET',
        contentType: 'application/json'
    }).done(function (result) {
        if (result) {
            let order = result.order
            let productId = result.productId;
            let sellerSection = $('#seller-section-' + order.seller.username);
            if (result.orderItemTotal === 0) {
                //remove empty order
                sellerSection.remove()
            } else {
                //update order information
                $('#cart-item-' + productId.toString()).remove();

                sellerSection.find('.tax').html(order.tax.toFixed(2))
                sellerSection.find('.total').html(order.total.toFixed(2))
                sellerSection.find('.sub-total').html((order.total - order.tax - order.shippingFee).toFixed(2))
            }

            $('#cart-count').html(result.cartItemTotal)
        }
    })
        .fail(function (error) {
            console.log(error)
        })

}