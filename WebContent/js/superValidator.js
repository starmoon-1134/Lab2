//***********************************************************
//����ԭ����֤��ܽ��иĽ�
//ʹ��ʱ����Ҫ��Ҫ������֤�ı�ǩ����check����
//��check="1"��ʱ��,����¼��Ϊ��,����������ݾͰ�reg���԰󶨵������������֤.
//��check="2"��ʱ��,��ֱ�Ӱ���reg�󶨵�������ʽ������֤.
//������ϣ�����ҽ�����,лл֧�� QQ6997467
//***********************************************************
//���������Ҫ��֤�ı�ǩ
(function($){
	$(document).ready(function(){
		$('select[tip],select[check],input[tip],input[check],textarea[tip],textarea[check]').tooltip();
		$('[closeAlterForm]').click(function() {
			 $("#back-layer-2").removeClass("blur");
			 $("#alterForm").animate({ height:'0',},{easing:'swing',speed:'slow'});
		});
		$(".closeBtn").click(function() {
			$("#back-layer-2").removeClass("blur");
			$(".showDetailLayer").animate({ height:'0',},{easing:'swing',speed:'slow'});
		})
		//$("input[name=AuthorName]").tooltip();
	});
})(jQuery);

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
            $("[submitAlterForm]").bind("click",{inputs:getthis}, alterSubmit);
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

//***************************************************************************************************************************************************
function alterSubmit(e) {
	var isValid = ValidConfirm(e.data.inputs);
	if(isValid){
		alterBook();
	}
}


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
					}
					
				}
				isSubmit = false;
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


function alterBook(){
	 $.ajax({
		 type:"post",
		 url:"alterBook",
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
	       	 if(resultString.indexOf("修改成功")>=0){
	    		 $("#back-layer-2").removeClass("blur");
				 $("#alterForm").animate({ height:'0',},{easing:'swing',speed:'slow'});
				 
				 //刷新之前的查询结果
				 window.frames[0].reflashQuery();
	    	 }
        },
		 error:function(resultString){
			 alert("不好意思，出错喽...");
		 }
	 });//$.ajax({})  end
	
};

//***************************************************************************************************************************************************