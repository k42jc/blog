/*!
 * 扩展工具栏上的保存按钮插件
 * @author liaoxudong
 * @updateTime 2016/04/20
 */

(function(){
	var factory = function(exports){
		var pluginName = "save-dialog";
		
		exports.fn.saveDialog = function(){
			var _this = this,
				cm = _this.cm,
				lang = _this.lang,
				editor = _this.editor,
				settings = _this.settings,
				cursor = cm.getCursor(),
				selection = cm.getSelection(),
				saveLang = lang.dialog.save,
				classPrefix = _this.classPrefix,
				iframeName = classPrefix+"save-iframe",
				dialogName = classPrefix+pluginName,
				dialog;
			
			cm.focus();
			
			var loading = function(show){
				var _loading = dialog.find('.'+classPrefix+'dialog-mask');
				_loading[(show)?"show":"hide"]();
			}
			//首次创建
			if(editor.find('.'+dialogName).length < 1){
				var guid = (new Date()).getTime();//设置唯一标识
				//设置表单提交action
				var action = saveLang.url+(saveLang.url.indexOf("?")>0?"&":"?")+"guid="+guid;
				
				var dialogContent = '<form id="form" action="'+action+'" target="'+iframeName+'" method="POST" enctype="multipart/form-data" class="'+classPrefix+'form">'
    			+'<iframe name="'+iframeName+'" id="'+iframeName+'" guid="'+guid+'"></iframe>'
    			+'<label>'+saveLang.labelNames.type+'</label><select id="type" name="type"><option selected="selected" value="0">请选择文章类型</option>'+saveLang.selectOptions.type+'</select>'
    			+'<br/>'
    			+'<label>'+saveLang.labelNames.title+'</label><input id="title" name="title" type="text" value=""/>'
    			+'<br/>'
    			/*+'<label>'+saveLang.viewImgURL+'</label><input id="viewImg" name="viewImg" type="text" value="http://"/>'
    			+'<div class="'+classPrefix+'file-input"><input type="file" name="editormd-image-file" accept="image/*" /><input type="button" value="本地上传" /></div><br/>'*/
    			+'<label>'+saveLang.labelNames.clazz+'</label><select id="clazz" name="clazz"><option selected="selected" value="0">请选择文章分类</option>'+saveLang.selectOptions.clazz+'</select>'
    			+'<br/>'
    			+'<label>'+saveLang.labelNames.label+'</label><input id="label" name="label" type="text" value=""/>'
    			+'<textarea hidden="hidden" name="content" id="content">'+_this.getHTML()+'</textarea>'
    			+'<br/></form>';
				
				//创建窗口
				dialog = _this.createDialog({
					//窗口常规属性
					title		: saveLang.title,
					width		: 400,
					height		: 295,
					name		: dialogName,
					content		: dialogContent,
					mask		: settings.dialogShowMask,
					drag		: settings.dialogDraggable,
					lockScreen	: settings.dialogLockScreen,
					maskStyle	: {
						opacity         : settings.dialogMaskOpacity,
                        backgroundColor : settings.dialogMaskBgColor
					},
					//设置按钮
					buttons		: {
						//发布按钮 自定义设置类型 支持设置为submit
						enter 	:[lang.buttons.publish, function(){
							//提交校验
							var canNotSubmit = false,form=this.find('form')[0];
							for(var index=0;index<form.length;index++){
								var _this = $(form[index]);
								if(_this.attr("id")){//只遍历有效元素
									//select
									if(_this.is('select')){
										if(_this.find('option:selected').val() == '0'){
											_this.css('border',"1px solid #d11149");
											_this.css('background-color',"rgba(209,17,73,0.1)");
											_this.focus();
											canNotSubmit = true;
										}
									}
									//input
									if(_this.is('input')){
										if(_this.val() == ''){
											_this.css('border',"1px solid #d11149");
											_this.css('background-color',"rgba(209,17,73,0.1)");
											_this.focus();
											canNotSubmit = true;
										}
									}
								}
							}
							if(canNotSubmit){
								return false;
							}else{
                                this.hide().lockScreen(false).hideMask();
                                this.find('#form').submit();
							}
                            return false;
						}],
						cancel	: [lang.buttons.cancel, function() {
                            this.hide().lockScreen(false).hideMask();

                            return false;
                        }]
					}
				});
				
				dialog.attr("id",classPrefix+"save-dialog"+guid);
			}
			
			dialog = editor.find("."+dialogName);
			this.dialogShowMask(dialog);
			this.dialogLockScreen();
			dialog.show();
			
			//绑定输入框修改值之后还原样式
			$("select").each(function(event){
                $(this).change(function () {  
                       if($(this).find("option:selected").val() != "0"){
                       	$(this).css('border','1px solid #ddd');
                       	$(this).css('background-color','#ffffff');
                       } 
               	});
                $(this).trigger("change"); 
            }); 
            $('input').each(function(event){
               	$(this).bind('input propertychange',function(event){
               		$(this).css({'border':'1px solid #ddd','background-color':'#ffffff'});
       			});
            });
		};
	};
	// 配置加载选项
	// CommonJS/Node.js
	if (typeof require === "function" && typeof exports === "object" && typeof module === "object")
    {
        module.exports = factory;
    }
	else if (typeof define === "function")  // AMD/CMD/Sea.js
    {
		if (define.amd) { // for Require.js

			define(["editormd"], function(editormd) {
                factory(editormd);
            });

		} else { // for Sea.js
			define(function(require) {
                var editormd = require("./../../editormd");
                factory(editormd);
            });
		}
	}
	else
	{
        factory(window.editormd);
	}
})();