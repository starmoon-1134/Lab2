$(document).ready(function(){
	 $(window).resize(resss);
	 resss();
	})
	 
/*        调整缩放                            */
function resss() {
	  var cutoff = $(".cut-off").outerWidth(true);
	  var table1 = $("#au").outerWidth(true);
	  var table2 = $("#bo").outerWidth(true);
	  var szoom = detectZoom();
	  var widtable = (cutoff+table1+table2) * szoom/100;
	  
	  if(szoom>=120){
		  $('.tableContainer').width('95%');
	  }
	  else if (szoom<=90) {
		  $('.tableContainer').width('60%');
	}
	  else {
		  $('.tableContainer').width(widtable);
	}
	  
	  var barpos = $(".cut-off").offset();
	  var barhe = $(".cut-off").outerHeight(true);
	  $("[submitAddForm]").offset({top:barpos.top+barhe+50,left:barpos.left-60});
	  }

function detectZoom (){ 
	  var ratio = 0,
	    screen = window.screen,
	    ua = navigator.userAgent.toLowerCase();
	 
	   if (window.devicePixelRatio !== undefined) {
	      ratio = window.devicePixelRatio;
	  }
	  else if (~ua.indexOf('msie')) {  
	    if (screen.deviceXDPI && screen.logicalXDPI) {
	      ratio = screen.deviceXDPI / screen.logicalXDPI;
	    }
	  }
	  else if (window.outerWidth !== undefined && window.innerWidth !== undefined) {
	    ratio = window.outerWidth / window.innerWidth;
	  }
	   
	   if (ratio){
	    ratio = Math.round(ratio * 100);
	  }
	   
	   return ratio;
	};

(function($){
	$(document).ready(function(){
		$('select[tip],select[check],input[tip],input[check],textarea[tip],textarea[check]').tooltip();
		//$("input[name=AuthorName]").tooltip();
	});
})(jQuery);



//////////////////////////////////////////Tooltip
(function($) {
    $.fn.tooltip = function(options){
		var getthis = this;
        var opts = $.extend({}, $.fn.tooltip.defaults, options);
        //alert(JSON.stringify($.fn.tooltip.defaults));
		//������ʾ��
        $('body').append('<table id="tipTable" class="tableTip"><tr><td  class="leftImage"></td> <td class="contenImage" align="left"></td> <td class="rightImage"></td></tr></table>');
		//�ƶ�������ظմ�������ʾ��
        // $(document).mouseout(function(){$('#tipTable').hide()});
        //$(document).blur(function(){$('#tipTable').hide()});
		
        
        
        this.each(function(){
            if($(this).attr('tip') != '')
            {
                //$(this).mouseover(function(){
            	$(this).focus(function(){
                    $('#tipTable').css({left:$.getLeft(this)+'px',top:$.getTop(this)+'px'});
                    $('.contenImage').html($(this).attr('tip'));
                    $('#tipTable').fadeIn("slow");
                }).blur(function(){
                	 $('#tipTable').hide();
                });
            }
            if($(this).attr('check') != '')
            {
				
                $(this).focus(function()
				{
                    $(this).removeClass('tooltipinputerr');
                }).blur(function(){
                    if($(this).attr('toupper') == 'true')
                    {
                        this.value = this.value.toUpperCase();
                    }
					if($(this).attr('check') != '')
					{
						
						if($(this).attr('check')=="1")
						{
							
							
							if($(this).attr('value')==null)
							{
								
								$(this).removeClass('tooltipinputerr').addClass('tooltipinputok');
							}else
							{
								
								var thisReg = new RegExp($(this).attr('reg'));
								if(thisReg.test(this.value))
								{
									$(this).removeClass('tooltipinputerr').addClass('tooltipinputok');
								}
								else
								{
									$(this).removeClass('tooltipinputok').addClass('tooltipinputerr');
								}
								
							}
						}
						if($(this).attr('check')=="2")
						{
							var thisReg = new RegExp($(this).attr('reg'));
								if(thisReg.test(this.value))
								{
									$(this).removeClass('tooltipinputerr').addClass('tooltipinputok');
								}
								else
								{
									$(this).removeClass('tooltipinputok').addClass('tooltipinputerr');
								}
						}			
					}
                    
                });
            }
        });
        if(opts.onsubmit)
        {
            $("[submitAddForm]").bind("click",{inputs:getthis}, addSubmit);
        }
    };

    $.extend({
        getWidth : function(object) {
            return object.offsetWidth;
        },

        getLeft : function(object) {//找到绝对位置
            var go = object;
            var oParent,oLeft = go.offsetLeft;
            while(go.offsetParent!=null) {
                oParent = go.offsetParent;
                oLeft += oParent.offsetLeft;
                go = oParent;
            }
            return oLeft;
        },

        getTop : function(object) {
            var go = object;
            var oParent,oTop = go.offsetTop;
            while(go.offsetParent!=null) {
                oParent = go.offsetParent;
                oTop += oParent.offsetTop;
                go = oParent;
            }
            return oTop + $(object).height()+ 5;
        },

        onsubmit : true
    });
    $.fn.tooltip.defaults = { onsubmit: true };
})(jQuery);

function ValidConfirm(inputs) {
	
    var isSubmit = true;
    inputs.each(function(){
		if($(this).attr('check')=="1")
			{
				if($(this).attr('value')==null)
				{
					$(this).removeClass('tooltipinputerr').addClass('tooltipinputok');
				}else
				{
					
					var thisReg = new RegExp($(this).attr('reg'));
					if(thisReg.test(this.value))
					{
						$(this).removeClass('tooltipinputerr').addClass('tooltipinputok');
					}
					else
					{
						$(this).removeClass('tooltipinputok').addClass('tooltipinputerr');
						isSubmit = false;
					}
					
				}
			}
        if($(this).attr('check')=="2")
			{
				var thisReg = new RegExp($(this).attr('reg'));
					if(thisReg.test(this.value))
					{
						$(this).removeClass('tooltipinputerr').addClass('tooltipinputok');
					}
					else
					{
						$(this).removeClass('tooltipinputok').addClass('tooltipinputerr');
						isSubmit = false;
					}
			}			
    });
    return isSubmit;
} 


function addSubmit(e) {
	var isValid = ValidConfirm(e.data.inputs);
	if(isValid){
		addBook();
	}
}

function addBook(){
	 $.ajax({
		 type:"post",
		 url:"addBook",
		 data:{//设置数据源
			 'ISBN':$("input[name=ISBN]").val(),
			 'Publisher':$("input[name=Publisher]").val(),
			 'PublishDate':$("input[name=PublishDate]").val(),
			 'Price':$("input[name=Price]").val(),
			 'Title':$("input[name=Title]").val(),
			 'AuthorID':$("input[name=AuthorID]").val(),
			 'AuthorAge':$("input[name=Age]").val(),
			 'AuthorCountry':$("input[name=Country]").val(),
		     'AuthorName':$("input[name=AuthorName]").val()
       },
       dataType:"json",//设置需要返回的数据类型
       success:function(resultString){
      	 	alert(resultString);
	       	 if(resultString.indexOf("成功")>=0){
	       		$("input[name=ISBN]").val("").removeClass("tooltipinputok");
				$("input[name=Publisher]").val("").removeClass("tooltipinputok");
				$("input[name=PublishDate]").val("").removeClass("tooltipinputok");
				$("input[name=Price]").val("").removeClass("tooltipinputok");
				$("input[name=Title]").val("").removeClass("tooltipinputok");
				$("input[name=AuthorID]").val("").removeClass("tooltipinputok");
				$("input[name=Age]").val("").removeClass("tooltipinputok");
				$("input[name=Country]").val("").removeClass("tooltipinputok");
			    $("input[name=AuthorName]").val("").removeClass("tooltipinputok");
	    	 }
       },
		 error:function(resultString){
			 alert("不好意思，出错喽...");
		 }
	 });//$.ajax({})  end
	
};

