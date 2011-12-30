jQuery.fn.DefaultValue = function(text){
    return this.each(function() {
        if(this.type != 'text' && this.type != 'password' && this.type != 'textarea')
            return;

        var fld_current=this;

        if(this.value=='')
            this.value=text;
        else
            return;

        $(this).focus(function() {
            if(this.value==text || this.value=='')
                this.value='';
        });

        $(this).blur(function() {
            if(this.value==text || this.value=='')
                this.value=text;
        });

        $(this).parents("form").each(function() {
            $(this).submit(function() {
                if(fld_current.value==text) {
                    fld_current.value='';
                }
            });
        });
    });
};
