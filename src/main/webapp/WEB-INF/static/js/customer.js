(function($) {
    $.kube = {
        living: false,
        listen: [],
        plugins: [],
        extend: ['appendForms', 'appendFormsData', 'getParams', 'getParamsData', 'disableBodyScroll', 'measureScrollbar', 'enableBodyScroll'],
        listenClass: function(plugin, name) {
            this.listen[plugin] = name;
        },
        plugin: function(name, version, opts, obj) {
            this.plugins.push(name);
            $.fn[name] = function(options) {
                var val = [];
                var args = Array.prototype.slice.call(arguments, 1);
                if (typeof options === 'string') {
                    this.each(function() {
                        var instance = $.data(this, name);
                        if (typeof instance !== 'undefined' && $.isFunction(instance[options])) {
                            var methodVal = instance[options].apply(instance, args);
                            if (methodVal !== undefined && methodVal !== instance) { val.push(methodVal); }
                        } else {
                            return $.error('No such method "' + options + '" for ' + name);
                        }
                    });
                } else {
                    this.each(function() {
                        $.data(this, name, {});
                        $.data(this, name, $.kube.execute(name, $, this, options));
                    });
                }
                if (val.length === 0) {
                    return this;
                } else if (val.length === 1) {
                    return val[0];
                } else {
                    return val;
                }
            };
            this.load(name, version, opts, obj);
        },
        load: function(name, version, opts, obj) {
            var self = this;
            var func = function(el, options) {
                var element = $(el).data('loaded', true);
                var instance = new func.prototype.options(element, options);
                instance.$element = element;
                instance.NAME = name;
                instance.VERSION = version;
                instance.callback = self.callback;
                for (var i = 0; i < this.extend.length; i++) { instance[this.extend[i]] = this[this.extend[i]]; }
                instance.init();
                return instance;
            };
            $[name] = func;
            $[name].opts = opts;
            func.fn = $[name].prototype = obj;
            func.fn.options = function(element, options) { this.opts = $.extend({}, $.extend(true, {}, opts), element.data(), options); };
            func.prototype.options.prototype = func.prototype;
            this.tool(name);
            this.live();
        },
        tool: function(name) {
            var listen = this.isListen(name);
            $(window).on('load.tools.' + name + listen, function() { $('[data-tools="' + name + '"]' + listen)[name](); });
        },
        live: function() {
            if (this.living) {
                return;
            }
            this.living = true;
            var self = this;
            $(function() {
                var listen = self.getListen();
                $('body').on('DOMNodeInserted', function(e) {
                    var $el = $(e.target);
                    var len = self.plugins.length;
                    var elements = $($el).find('[data-tools]' + listen).add($el);
                    elements.each(function() {
                        var $node = $(this);
                        for (var i = 0; i < len; i++) {
                            var name = self.plugins[i];
                            var run = false;
                            if ($node.data('loaded') !== true) {
                                if ($node.data('tools') === name) { run = true; }
                                if (typeof self.listen[name] !== 'undefined' && $node.hasClass(self.listen[name])) { run = true; }
                            }
                            if (run) { $node[name](); }
                        }
                    });
                });
            });
        },
        getListen: function() {
            var str = [];
            for (var key in this.listen) { str.push('.' + key); }
            return (str.length > 0) ? ',' + str.join(',') : '';
        },
        isListen: function(name) {
            if (typeof this.listen[name] !== 'undefined') {
                return ',.' + this.listen[name];
            }
            return '';
        },
        execute: function(name, context) {
            var args = [].slice.call(arguments).splice(2);
            return context[name].apply(this, args);
        },
        callback: function(obj, type, e, data) {
            var el = (typeof obj.callback.$element === 'undefined') ? obj.$element[0] : obj.callback.$element[0];
            var events = $._data(el, 'events');
            if (events && typeof events[type] !== 'undefined') {
                var value = [];
                var len = events[type].length;
                for (var i = 0; i < len; i++) {
                    var namespace = events[type][i].namespace;
                    if (namespace === 'tools.' + this.NAME || namespace === this.NAME + '.tools') {
                        var callback = events[type][i].handler;
                        value.push((typeof data === 'undefined') ? callback.call(obj, e) : callback.call(obj, e, data));
                    }
                }
                return (value.length === 1) ? value[0] : value;
            }
            return (typeof data === 'undefined') ? e : data;
        },
        appendForms: function(data) {
            if (!this.opts.appendForms) {
                return data;
            }
            var forms = this.opts.appendForms.split(',');
            $.each(forms, function(i, s) {
                var form = $.trim(s);
                data += '&' + $(form).serialize();
            });
            return data;
        },
        appendFormsData: function(data) {
            if (!this.opts.appendForms) {
                return data;
            }
            var forms = this.opts.appendForms.split(',');
            $.each(forms, function(i, s) {
                var form = $.trim(s);
                var formData = $(form).serializeArray();
                $.each(formData, function(z, f) { data.append(f.name, f.value); });
            });
            return data;
        },
        getParams: function(data) {
            if (typeof data === 'undefined') { data = ''; }
            if (!this.opts.params) {
                return data;
            }
            var start = (data === '') ? '' : '&';
            this.opts.params = $.trim(this.opts.params.replace('{', '').replace('}', ''));
            var properties = this.opts.params.split(',');
            var obj = {};
            $.each(properties, function(k, v) {
                var tup = v.split(':');
                tup[0] = $.trim(tup[0]).replace(/^\'(.*?)\'$/, '$1');
                tup[1] = $.trim(tup[1]).replace(/^\'(.*?)\'$/, '$1');
                obj[$.trim(tup[0])] = tup[1];
            });
            var str = [];
            $.each(obj, $.proxy(function(k, v) { str.push(k + "=" + v); }, this));
            str = str.join("&");
            data += start + str;
            return data;
        },
        getParamsData: function(data) {
            if (typeof data === 'undefined') { data = {}; }
            if (!this.opts.params) {
                return data;
            }
            this.opts.params = $.trim(this.opts.params.replace('{', '').replace('}', ''));
            var properties = this.opts.params.split(',');
            $.each(properties, function(k, v) {
                var tup = v.split(':');
                var value = $.trim(tup[1]).replace(/^\'(.*?)\'$/, '$1');
                var name = $.trim(tup[0]).replace(/^\'(.*?)\'$/, '$1');
                data.append(name, value);
            });
            return data;
        },
        disableBodyScroll: function() {
            var $body = $('html');
            var windowWidth = window.innerWidth;
            if (!windowWidth) {
                var documentElementRect = document.documentElement.getBoundingClientRect();
                windowWidth = documentElementRect.right - Math.abs(documentElementRect.left);
            }
            var isOverflowing = document.body.clientWidth < windowWidth;
            var scrollbarWidth = this.measureScrollbar();
            $body.css('overflow', 'hidden');
            if (isOverflowing) { $body.css('padding-right', scrollbarWidth); }
        },
        measureScrollbar: function() {
            var $body = $('body');
            var scrollDiv = document.createElement('div');
            scrollDiv.className = 'tools-scrollbar-measure';
            $body.append(scrollDiv);
            var scrollbarWidth = scrollDiv.offsetWidth - scrollDiv.clientWidth;
            $body[0].removeChild(scrollDiv);
            return scrollbarWidth;
        },
        enableBodyScroll: function() { $('html').css({ 'overflow': '', 'padding-right': '' }); }
    };
})(jQuery);
(function($) {
    'use strict';
    var opts = { target: false };
    $.kube.plugin('togglenav', '1.0', opts, {
        init: function() {
            this.$target = $(this.opts.target);
            this.$element.on('click.tools.togglenav', $.proxy(this.toggle, this));
            this.build();
            $(window).resize($.proxy(this.build, this));
        },
        build: function() {
            var mq = window.matchMedia("(max-width:767px)");
            if (mq.matches) { this.$target.removeClass('open').slideUp(200); } else { this.$target.addClass('open').slideDown(400); }
        },
        toggle: function(e) {
            e.preventDefault();
            if (this.isTargetHide()) {
                this.$target.addClass('open').slideDown(400);
                this.callback(this, 'show', this.$target);
            } else {
                this.$target.removeClass('open').slideUp(200);
                this.callback(this, 'hide', this.$target);
            }
        },
        isTargetHide: function() {
            return (this.$target.hasClass('open')) ? false : true;
        }
    });
})(jQuery);
(function($) {
    'use strict';
    var opts = { url: false, trigger: false, target: false, delay: 10, errorClassName: 'input-error', spanClassName: 'error', send: true, progress: false };
    $.kube.plugin('validate', '1.0', opts, {
        init: function() {
            this.$element.attr('novalidate', 'novalidate');
            if (this.opts.target !== false) {
                this.$element.on('click', $.proxy(function(e) {
                    e.preventDefault();
                    this.sendContainer();
                    return false;
                }, this));
            } else if (this.opts.trigger === false) {
                this.$element.on('submit', $.proxy(function() {
                    this.send();
                    return false;
                }, this));
            } else {
                this.$element.submit(function() {
                    return false;
                });
                $(this.opts.trigger).off('click.tools.validate');
                $(this.opts.trigger).on('click.tools.validate', $.proxy(this.send, this));
            }
        },
        sendContainer: function() {
            var data = [];
            $(this.opts.target).find(':input').not('button,[type=submit]').each(function() {
                var $el = $(this);
                var name = $el.attr('name');
                if (typeof name !== 'undefined') {
                    data.push(name + '=' + encodeURIComponent($el.val()));
                }
            });
            this.callback(this, 'send');
            $.ajax({ url: this.opts.url, type: 'post', data: data.join("&"), success: $.proxy(this.parse, this) });
        },
        send: function() {
            this.disabled();
            $('.CodeMirror').each(function(i, el) {
                el.CodeMirror.save();
            });
            this.callback(this, 'send');
            if (!this.opts.send) {
                return;
            }
            $.ajax({
                url: this.opts.url,
                type: 'post',
                data: this.$element.serialize(),
                success: $.proxy(this.parse, this)
            });
        },
        parse: function(jsonString) {
            this.enabled();
            this.clear();
            var obj = {};
            if (jsonString !== '') {
                jsonString = jsonString.replace(/^\[/, '');
                jsonString = jsonString.replace(/\]$/, '');
                try {
                    obj = $.parseJSON(jsonString);
                } catch (e) {
                    obj = jsonString;
                }
            }
            if (obj.type === 'error') {
                $.each(obj.errors, $.proxy(function(name, text) {
                    var $form = this.form();
                    var $el = $($form.find('[name=' + name + ']'));
                    var redactor = $el.data('redactor');
                    if (typeof redactor !== 'undefined') {
                        redactor.core.box().addClass(this.opts.errorClassName);
                    }
                    $el.addClass(this.opts.errorClassName);
                    if (text !== '') {
                        this.showError($el, name, text, redactor);
                    }
                }, this));
                this.callback(this, 'error', obj.errors);
            } else {
                if (obj.type === 'html') {
                    $.each(obj.data, $.proxy(function(i, s) { $(i).html(this.stripslashes(this.urldecode(s))); }, this));
                } else if (obj.type === 'command') {
                    $.each(obj.data, $.proxy(function(i, s) { $(s)[i](); }, this));
                } else if (obj.type === 'location') {
                    top.location.href = obj.data;
                } else if (obj.type === 'message') {
                    this.showMessage(obj);
                }
                this.callback(this, 'success', obj);
            }
        },
        showMessage: function(obj) {
            var text = '';
            if ($.isArray(obj.data)) {
                text = '<ul>';
                var len = obj.data.length;
                for (var i = 0; i < len; i++) {
                    text += '<li>' + obj.data[i] + '</li>';
                }
                text += '</ul>';
            } else {
                text = obj.data;
            }
            var theme = (typeof obj.theme !== 'undefined') ? obj.theme : 'error';
            var message = $('<div class="message message-' + theme + ' validate-message" />').html(text);
            $('body').append(message);
            message.show().on('click.tools.validate', function() {
                message.remove();
                message.off('click.tools.validate');
            });
            if (this.opts.delay) {
                setTimeout(function() { message.fadeOut(); }, this.opts.delay * 1000);
            }
        },
        showError: function($el, name, text, redactor) {
            $('#' + name + '-error').addClass(this.opts.spanClassName).text(text).show();
            var eventName = 'keyup';
            var tag = $el.prop('tagName');
            var type = $el.prop('type');
            if (tag === 'SELECT' || type === 'checkbox' || type === 'radio') {
                eventName = 'change';
            }
            $el.on(eventName + '.tools.validate', $.proxy(function() {
                $el.removeClass(this.opts.errorClassName);
                if (typeof redactor !== 'undefined') {
                    redactor.$box.removeClass(this.opts.errorClassName);
                }
                $('#' + name + '-error').removeClass('validate-error').html('').hide();
                $el.off(eventName + '.tools.validate');
            }, this));
        },
        clear: function() {
            var $form = this.form();
            $form.find('.' + this.opts.errorClassName).each($.proxy(function(i, s) {
                var name = $(s).attr('name');
                $('#' + name + '-error').removeClass(this.opts.spanClassName).html('').hide();
                $(s).removeClass(this.opts.errorClassName);
            }, this));
            $('.validate-error').removeClass('validate-error').html('').hide();
            $('.validate-message').remove();
        },
        disabled: function() {
            if (this.opts.progress && $.progress) {
                $.progress.show();
            }
            var $form = this.form();
            $form.find('button').attr('disabled', true);
        },
        enabled: function() {
            if (this.opts.progress && $.progress) {
                $.progress.hide();
            }
            var $form = this.form();
            $form.find('button').removeAttr('disabled');
        },
        form: function() {
            return (this.opts.target !== false) ? $(this.opts.target) : this.$element;
        },
        urldecode: function(str) {
            return decodeURIComponent(str.replace(/\+/g, '%20'));
        },
        stripslashes: function(str) {
            return (str + '').replace(/\0/g, '0').replace(/\\([\\'"])/g, '$1');
        }
    });
})(jQuery);
