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


