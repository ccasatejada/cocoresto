(function ($) {
    $(document).ready(function () {

        // totop
        $('a[href^="#top"]').click(
                function () {
                    var the_id = $(this).attr("href");
                    $('html,body').animate({
                        scrollTop: $(the_id).offset().top
                    }, 'slow');
                    return false;
                }
        );

        $(window).scroll(function () {
            if ($(this).scrollTop() !== 0) {
                $("#totop").fadeIn();
            } else {
                $("#totop").fadeOut();
            }
        });

        // tooltip
        $('[data-toggle="tooltip"]').tooltip();


        $(window).bind('click', function (event) {
            if (event.target.href) {
                $(window).unbind('beforeunload');
            }
        });

        $(window).bind('submit', function (event) {
            $(window).unbind('beforeunload');
        });

        $(window).bind('reset', function (event) {
            $(window).unbind('beforeunload');
        });

        $(window).bind('beforeunload', function (event) {
            $.ajax({
                url: 'Ajax',
                type: 'POST',
                data: 'task=removeEmployee',
            });
            //return 'Vous allez être déconnecté';
        });


        /************************************************************************************/
        /************************************ Open Order ************************************/
        /************************************************************************************/

        // initial hide fields group
        $("#editOrder.new #customerTableGroup").hide();
        $("#editOrder.new #nbTabletGroup").hide();

        // update available tables
        $('#editOrder #people').on('change', function () {
            if ($(this).val() > 0) {
                $("#editOrder #customerTableGroup").show();
                $("#editOrder #customerTable").empty();
                $("#editOrder #nbTabletGroup").hide();
                $("#editOrder #nbTabletGroup [type='number']").val(1);
                $.ajax({
                    url: 'Ajax',
                    type: 'POST',
                    data: 'task=customerTable&get=available&nb=' + $(this).val(),
                    dataType: 'html',
                    success: function (html) {
                        $(html).appendTo("#editOrder #customerTable");
                    },
                });
            }
        });

        $('#editOrder #customerTableGroup select').on('change', function () {
            if ($(this).val() > 0) {
                $("#editOrder #nbTabletGroup").show();
                $("#editOrder #nbTabletGroup [type='number']").val(1);
                $.ajax({
                    url: 'Ajax',
                    type: 'POST',
                    data: 'task=customerTable&get=nbTablet&table=' + $(this).val(),
                    dataType: 'text',
                    success: function (data) {
                        $("#editOrder #nbTabletGroup [type='number']").attr('max', data)
                    },
                });
            }
        });


        $('#getDrinks').on('click', function (event) {
            event.preventDefault();
            $.ajax({
                url: 'FrontController?option=menu&task=getDrinks',
                type: 'POST',
                data: $('#drinkMenu').html(),
                dataType: 'html',
                success: function (data) {
                    $('#menuContent').empty();
                    $('#menuContent').html(data);
                }
            });
        });

        $('.drinkDetail').on('click', function (event) {
            event.preventDefault();
            $.ajax({
                url: $(this).attr('href'),
                type: 'POST',
                data: $('#drinkDetails').html(),
                dataType: 'html',
                success: function (data) {
                    $('#menuContent').empty();
                    $('#menuContent').html(data);
                }
            });
        });

        $('#getDishes').on('click', function (event) {
            event.preventDefault();
            $.ajax({
                url: 'FrontController?option=menu&task=getDishes',
                type: 'POST',
                data: $('#dishMenu').html(),
                dataType: 'html',
                success: function (data) {
                    $('#menuContent').empty();
                    $('#menuContent').html(data);
                }
            });
        });

        $('.dishDetail').on('click', function (event) {
            event.preventDefault();
            $.ajax({
                url: $(this).attr('href'),
                type: 'POST',
                data: $('#dishDetails').html(),
                dataType: 'html',
                success: function (data) {
                    $('#menuContent').empty();
                    $('#menuContent').html(data);
                }
            });
        });

        $('#getCombos').on('click', function (event) {
            event.preventDefault();
            $.ajax({
                url: 'FrontController?option=menu&task=getCombos',
                type: 'POST',
                data: $('#comboMenu').html(),
                dataType: 'html',
                success: function (data) {
                    $('#menuContent').empty();
                    $('#menuContent').html(data);
                }
            });
        });

        $('.comboDetail').on('click', function (event) {
            event.preventDefault();
            $.ajax({
                url: $(this).attr('href'),
                type: 'POST',
                data: $('#comboDetails').html(),
                dataType: 'html',
                success: function (data) {
                    $('#menuContent').empty();
                    $('#menuContent').html(data);
                }
            });
        });

//        $('#drinkFormats input[type=checkbox]').on('change', function (event) {
//            event.preventDefault();
//            $.ajax({
//                url: '',
//                type: 'POST',
//                data: '<label class="col-sm-2 control-label">Prix Unitaire HT ${loop.index+1}: </label>'
//                        + '<div class="col-sm-10">'
//                        + '<input type="text" class="form-control" name="price" value="${price.price}">'
//                        + '</div>',
//                dataType: 'html',
//                success: function (data) {
//                    //$('#drinkPrice').empty();
//                    $('#drinkPrice').html(data);
//                }
//            });
//        });






    });
})(jQuery);

(function () {

    var drinkPrice = document.getElementById("drinkPrice");
    var drinkPricesLabels = document.querySelectorAll("#drinkPrice div label");
    var drinkPricesInputs = document.querySelectorAll("#drinkPrice div div input[type=text]");
    var format = document.querySelectorAll("#drinkFormats input[type=checkbox]:checked");
    var formats = $("#drinkFormats input:checkbox:not(:checked)");
    
    for (var j = 0; j < format.length; j++) {
        console.log(drinkPricesLabels[j].textContent);
        drinkPricesLabels[j].textContent = "Prix unitaire HT " + format[j].nextSibling.nextSibling.textContent + " : ";
        console.log(format[j].textContent);
        drinkPricesInputs[j].id = format[j].nextSibling.nextSibling.textContent;
        drinkPricesInputs[j].name = format[j].nextSibling.nextSibling.textContent;
        console.log(drinkPricesInputs[j].id);
        drinkPricesLabels[j].parentNode.id = format[j].nextSibling.nextSibling.textContent;
        console.log(drinkPricesLabels[j].parentNode.id);

        format[j].addEventListener("change", function (e) {
            var checkedFormat = e.target.nextSibling.nextSibling;
            console.log(checkedFormat);
            var divInput = document.createElement("div");
            divInput.id = checkedFormat.textContent;

            var labelInput = document.createElement("label");
            var labelText = document.createTextNode('Prix Unitaire HT ' + checkedFormat.textContent);
            labelInput.className = "col-sm-2 control-label";
            labelInput.appendChild(labelText);

            var divPrice = document.createElement("div");
            divPrice.className = "col-sm-10";

            var priceInput = document.createElement("input");
            priceInput.type = "text";
            priceInput.className = "form-control";
            priceInput.name = checkedFormat.textContent;
            priceInput.setAttribute("value", "");

            divPrice.appendChild(priceInput);
            divInput.appendChild(labelInput);
            divInput.appendChild(divPrice);

            if (e.target.checked) {
                drinkPrice.appendChild(divInput);
            } else if (!e.target.checked) {
                drinkPrice.removeChild(document.getElementById(checkedFormat.textContent));

            }
        }, false);
    }

    for (var i = 0; i < formats.length; i++) {

        formats[i].addEventListener("change", function (e) {
            var checkedFormat = e.target.nextSibling.nextSibling;
            console.log(checkedFormat);
            var divInput = document.createElement("div");
            divInput.id = checkedFormat.textContent;

            var labelInput = document.createElement("label");
            var labelText = document.createTextNode('Prix Unitaire HT ' + checkedFormat.textContent);
            labelInput.className = "col-sm-2 control-label";
            labelInput.appendChild(labelText);

            var divPrice = document.createElement("div");
            divPrice.className = "col-sm-10";

            var priceInput = document.createElement("input");
            priceInput.type = "text";
            priceInput.className = "form-control";
            priceInput.name = checkedFormat.textContent;
            priceInput.setAttribute("value", "");

            divPrice.appendChild(priceInput);
            divInput.appendChild(labelInput);
            divInput.appendChild(divPrice);

            if (e.target.checked) {
                drinkPrice.appendChild(divInput);
            } else if (!e.target.checked) {
                drinkPrice.removeChild(document.getElementById(checkedFormat.textContent));

            }
        }, false);
    }

})();

