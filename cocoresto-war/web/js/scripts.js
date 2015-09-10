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

        /************************************************************************************/
        /*************************************** Menu ***************************************/
        /************************************************************************************/
        
        $('#customerMenu a').on('click', function (event) {
            event.preventDefault();
            
            $('#customerMenu a').each(function(i) {
                $(this).removeClass('active');
            });
            $(this).addClass('active');
            
            var query = $(this).data('query');

            $.ajax({
                url: 'Ajax',
                type: 'POST',
                data: 'task=menu&list=' + query,
                dataType: 'html',
                success: function (html) {
                    $('#menuContent').empty();
                    $(html).appendTo("#menuContent");
                }
            });
        });
        
        
        $('#customerMenu #dishes').trigger('click');
        
//
//        
//
//        $('.drinkDetail').on('click', function (event) {
//            event.preventDefault();
//            $.ajax({
//                url: $(this).attr('href'),
//                type: 'POST',
//                data: $('#drinkDetails').html(),
//                dataType: 'html',
//                success: function (data) {
//                    $('#menuContent').empty();
//                    $('#menuContent').html(data);
//                }
//            });
//        });
//
//        $('.dishDetail').on('click', function (event) {
//            event.preventDefault();
//            $.ajax({
//                url: $(this).attr('href'),
//                type: 'POST',
//                data: $('#dishDetails').html(),
//                dataType: 'html',
//                success: function (data) {
//                    $('#menuContent').empty();
//                    $('#menuContent').html(data);
//                }
//            });
//        });
//
//        $('.comboDetail').on('click', function (event) {
//            event.preventDefault();
//            $.ajax({
//                url: $(this).attr('href'),
//                type: 'POST',
//                data: $('#comboDetails').html(),
//                dataType: 'html',
//                success: function (data) {
//                    $('#menuContent').empty();
//                    $('#menuContent').html(data);
//                }
//            });
//        });


    });
})(jQuery);

(function () {

    var drinkPrice = document.getElementById("drinkPrice");
//    var formats = document.querySelectorAll("#drinkFormats input[type=checkbox]");
    var formats = $("#drinkFormats input[type=checkbox]");
//    var drinkPrices = document.querySelectorAll("#drinkFormats div input[type=hidden]");
    var drinkPrices = $("#drinkFormats input[type=hidden]");
    
    var checkIndex = 0;
    for (var i = 0; i < formats.length; i++) {

        if (formats[i].checked) {
            var checkedFormat = formats[i].nextSibling.nextSibling;

            var divInput = document.createElement("div");
            divInput.id = "price" + checkedFormat.textContent;

            var labelInput = document.createElement("label");
            var labelText = document.createTextNode('Prix Unitaire HT ' + checkedFormat.textContent);
            labelInput.className = "col-sm-2 control-label";
            labelInput.setAttribute("for", checkedFormat.textContent);
            labelInput.appendChild(labelText);

            var divPrice = document.createElement("div");
            divPrice.className = "col-sm-10";
            divPrice.id = checkedFormat.textContent;

            var priceInput = document.createElement("input");
            priceInput.type = "text";
            priceInput.className = "form-control";
            priceInput.name = checkedFormat.textContent;
            priceInput.setAttribute("value", drinkPrices[checkIndex].value);
            checkIndex++;

            divPrice.appendChild(priceInput);
            divInput.appendChild(labelInput);
            divInput.appendChild(divPrice);

            drinkPrice.appendChild(divInput);
        }
        formats[i].addEventListener("change", function (e) {

            if (e.target.checked) {
                drinkPrice.appendChild(getDivInput(e));
            } else if (!e.target.checked) {
                drinkPrice.removeChild(document.getElementById("price" + e.target.nextSibling.nextSibling.textContent));
            }
        }, false);
    }

    function getDivInput(element) {
        var checkedFormat = element.target.nextSibling.nextSibling;
        var divInput = document.createElement("div");
        divInput.id = "price" + checkedFormat.textContent;

        var labelInput = document.createElement("label");
        var labelText = document.createTextNode('Prix Unitaire HT ' + checkedFormat.textContent);
        labelInput.className = "col-sm-2 control-label";
        labelInput.setAttribute("for", checkedFormat.textContent);
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

        return divInput;
    }
})();

