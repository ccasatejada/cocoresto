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
        

        
        
    });
})(jQuery);
