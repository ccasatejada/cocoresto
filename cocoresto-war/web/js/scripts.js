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

        /************************************************************************************/
        /************************************ Open Order ************************************/
        /************************************************************************************/

        // initial hide fields group
        $("#newOrder #customerTableGroup").hide();
        $("#newOrder #nbTabletGroup").hide();

        // update available tables
        $('#newOrder #people').on('change', function () {
            if ($(this).val() > 0) {
                $("#newOrder #customerTableGroup").show();
                $("#newOrder #customerTable").empty ();
                $("#newOrder #nbTabletGroup").hide();
                $("#newOrder #nbTabletGroup [type='number']").val(1);
                $.ajax({
                    url: 'Ajax',
                    type: 'POST',
                    data: 'task=customerTable&get=available&nb=' + $(this).val(),
                    dataType: 'html',
                    success: function (html) {
                        $(html).appendTo("#newOrder #customerTable");
                    },
                });
            }
        });
        
        $('#newOrder #customerTableGroup select').on('change', function () {
            if ($(this).val() > 0) {
                $("#newOrder #nbTabletGroup").show();
                $("#newOrder #nbTabletGroup [type='number']").val(1);
                $.ajax({
                    url: 'Ajax',
                    type: 'POST',
                    data: 'task=customerTable&get=nbTablet&table=' + $(this).val(),
                    dataType: 'text',
                    success: function (data) {
                        $("#newOrder #nbTabletGroup [type='number']").attr('max', data)
                    },
                });
            }
        });
        
        
        $('#getDrinks').on('click', function (event) {
           event.preventDefault();
           $('#menuContent').empty();
           $('#menuContent').load('/WEB-INF/menu/drinkMenu.jsp');
           $('#menuContent').show();
        });
        
        

    });
})(jQuery);
