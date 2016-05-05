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
    var opts = { toggle: true, collapse: true, control: true, active: false, titleClass: 'accordion-title', panelClass: 'accordion-panel' };
    $.kube.plugin('accordion', '1.0', opts, {
        init: function() {
            this.$element.addClass('accordion');
            this.titles = this.$element.find('.' + this.opts.titleClass);
            if (this.opts.control) { this.titles.append($('<span />').addClass('accordion-toggle')); }
            this.titles.on('click.tools.accordion', $.proxy(this.toggle, this));
            this.panels = this.$element.find('.' + this.opts.panelClass);
            if (this.opts.collapse) { this.closeAll(); } else { this.openAll(); }
            if (this.opts.active) {
                this.get(this.opts.active);
                this.covert('open');
            }
        },
        toggle: function(e) {
            var target = (typeof e === 'string') ? this.$element.find('[href=' + e + ']') : $(e.target);
            if (typeof e !== 'string') { e.preventDefault(); }
            var $target = target.closest('.' + this.opts.titleClass);
            var opened = $target.hasClass('accordion-title-opened');
            if (this.opts.toggle) { this.closeAll($target); }
            this.$title = $target;
            this.$panel = $(this.$title.attr('href'));
            return (opened) ? this.close() : this.open();
        },
        get: function(hash) {
            if (typeof hash !== 'undefined') {
                this.$title = this.$element.find('[href=' + hash + ']');
                this.$panel = $(this.$title.attr('href'));
            }
        },
        isOpened: function(hash) {
            this.get(hash);
            return (this.$title.hasClass('accordion-title-opened'));
        },
        isClosed: function(hash) {
            this.get(hash);
            return (this.$title.hasClass('accordion-title-closed'));
        },
        open: function(hash) {
            if (this.isOpened(hash)) {
                return;
            }
            this.action('open');
        },
        close: function(hash) {
            if (this.isClosed(hash)) {
                return;
            }
            this.action('close');
        },
        action: function(action) {
            this.callback(this, action, this.$title, this.$panel);
            this.status(action);
            if (action === 'close') { this.$panel.slideUp(250); } else { this.$panel.slideDown(500); }
            this.callback(this, action + 'ed', this.$title, this.$panel);
        },
        covert: function(action) {
            this.status(action);
            if (action === 'close') { this.$panel.hide(); } else { this.$panel.show(); }
        },
        openAll: function() { this.actionAll('open'); },
        closeAll: function(except) { this.actionAll('close', except); },
        actionAll: function(action, except) {
            this.titles.not(except).each($.proxy(function(i, s) {
                this.$title = $(s);
                this.$panel = $(this.$title.attr('href'));
                this.status(action);
                if (action === 'close') { this.$panel.hide(); } else { this.$panel.show(); }
            }, this));
        },
        status: function(command) {
            var items = { toggle: this.$title.find('span.accordion-toggle'), title: this.$title, panel: this.$panel };
            $.each(items, function(i, s) {
                var prefix = 'accordion-' + i + '-';
                var remove = (command === 'close') ? 'opened' : 'closed';
                var add = (command === 'close') ? 'closed' : 'opened';
                s.removeClass(prefix + remove).addClass(prefix + add);
            });
        }
    });
})(jQuery);
(function($) {
    'use strict';
    var opts = {};
    $.kube.plugin('alert', '1.0', opts, {
        init: function() {
            this.$close = $('<span class="close" />').html('&times;');
            this.$element.append(this.$close);
            this.$close.on('click.tools.alert', $.proxy(this.hide, this));
        },
        hide: function(e) {
            e.preventDefault();
            this.$element.fadeOut(400, $.proxy(function() {
                this.$close.off('click.tools.alert');
                this.$element.addClass('hide');
            }, this));
        }
    });
})(jQuery);
(function($) {
    'use strict';
    var opts = { url: false, min: 2, name: false, params: false, appendForms: false };
    $.kube.plugin('autocomplete', '1.0', opts, {
        init: function() {
            this.result = $('<ul class="autocomplete">').hide();
            this.pos = this.$element.offset();
            this.elementHeight = this.$element.innerHeight();
            $('body').append(this.result);
            this.placement = (($(document).height() - (this.pos.top + this.elementHeight)) < this.result.height()) ? 'top' : 'bottom';
            $(document).on('click.tools.autocomplete', $.proxy(this.hide, this));
            this.timeout = null;
            this.$element.on('keyup.tools.autocomplete', $.proxy(this.keyup, this));
        },
        keyup: function(e) {
            clearTimeout(this.timeout);
            var value = this.$element.val();
            if (value.length >= this.opts.min) {
                this.$element.addClass('autocomplete-in');
                this.result.addClass('autocomplete-open');
                this.listen(e);
            } else { this.hide(); }
        },
        lookup: function() {
            var name = (this.opts.name) ? this.opts.name : this.$element.attr('name');
            var data = name + '=' + this.$element.val();
            data = this.getParams(data);
            data = this.appendForms(data);
            $.ajax({
                url: this.opts.url,
                type: 'post',
                data: data,
                success: $.proxy(function(json) {
                    var data = $.parseJSON(json);
                    this.result.html('');
                    $.each(data, $.proxy(function(i, s) {
                        var li = $('<li>');
                        var a = $('<a href="#" rel="' + i + '">').text(s).on('click', $.proxy(this.set, this));
                        li.append(a);
                        this.result.append(li);
                    }, this));
                    var top = (this.placement === 'top') ? (this.pos.top - this.result.height() - this.elementHeight) : (this.pos.top + this.elementHeight);
                    this.result.css({ top: top + 'px', left: this.pos.left + 'px' });
                    this.result.show();
                    this.active = false;
                }, this)
            });
        },
        listen: function(e) {
            switch (e.keyCode) {
                case 40:
                    e.preventDefault();
                    this.select('next');
                    break;
                case 38:
                    e.preventDefault();
                    this.select('prev');
                    break;
                case 13:
                    e.preventDefault();
                    this.set();
                    break;
                case 27:
                    e.preventDefault();
                    this.hide();
                    break;
                default:
                    this.timeout = setTimeout($.proxy(function() { this.lookup(); }, this), 300);
                    break;
            }
        },
        select: function(type) {
            var $links = this.result.find('a');
            var size = $links.size();
            var $active = this.result.find('a.active');
            $active.removeClass('active');
            var $item = (type === 'next') ? $active.parent().next().children('a') : $active.parent().prev().children('a');
            if ($item.size() === 0) { $item = (type === 'next') ? $links.eq(0) : $links.eq(size - 1); }
            $item.addClass('active');
            this.active = $item;
        },
        set: function(e) {
            var $el = $(this.active);
            if (e) {
                e.preventDefault();
                $el = $(e.target);
            }
            var id = $el.attr('rel');
            var value = $el.html();
            this.$element.val(value);
            this.callback(this, 'select', id, value);
            this.hide();
        },
        hide: function(e) {
            if (e && ($(e.target).hasClass('autocomplete-in') || $(e.target).hasClass('autocomplete-open') || $(e.target).parents().hasClass('autocomplete-open'))) {
                return;
            }
            this.$element.removeClass('autocomplete-in');
            this.result.removeClass('autocomplete-open');
            this.result.hide();
        }
    });
})(jQuery);
(function($) {
    'use strict';
    var opts = { minHeight: '50px' };
    $.kube.plugin('autoresize', '1.0', opts, {
        init: function() {
            var minHeight = false;
            if (this.opts.minHeight) {
                this.$element.css('min-height', this.opts.minHeight);
                minHeight = parseInt(this.opts.minHeight);
            }
            var diff = parseInt(this.$element.css('paddingBottom')) + parseInt(this.$element.css('paddingTop'));
            if (this.equalText(this.$element.val())) { this.$element.height(this.$element[0].scrollHeight - diff); }
            this.$element.on('input.tools.autoresize keyup.tools.autoresize', function() {
                if (minHeight && this.scrollHeight < minHeight) {
                    return;
                }
                var $window = $(window);
                var scrollPosition = $window.scrollTop();
                $(this).height(0).height(this.scrollHeight - diff);
                $window.scrollTop(scrollPosition);
            });
        },
        equalText: function(text) {
            return (text.replace(/\s/g, '').length > 0);
        }
    });
})(jQuery);
(function($) {
    'use strict';
    var opts = { target: false };
    $.kube.plugin('buttons', '1.0', opts, {
        init: function() {
            if (!this.opts.target) {
                return;
            }
            this.buttons = this.$element.find('.btn,button');
            this.value = $(this.opts.target).val();
            this.buttons.each($.proxy(function(i, s) {
                var $s = $(s);
                this.setDefault($s);
                $s.click($.proxy(function(e) {
                    e.preventDefault();
                    this.setBasic($s);
                }, this));
            }, this));
        },
        setDefault: function($el) {
            if (this.value === $el.val()) { this.setActive($el); }
        },
        setBasic: function($el) {
            this.setInActive(this.buttons);
            this.setActive($el);
            $(this.opts.target).val($el.val());
        },
        setActive: function($el) { $el.attr('disabled', true); },
        setInActive: function($el) { $el.removeAttr('disabled'); }
    });
})(jQuery);
(function($) {
    'use strict';
    var opts = {};
    $.kube.listenClass('cardform', 'card-form');
    $.kube.plugin('cardform', '1.0', opts, {
        init: function() {
            this.$element.addClass('card-form-view').find('input,select,textarea').attr('readonly', true);
            this.buttons = this.$element.find('row .btn,row button').hide();
            this.$saveButton = this.$element.find('.card-form-save');
            this.$cancelButton = this.$element.find('.card-form-cancel');
            this.$editButton = this.$element.find('.card-form-toggle');
            this.$editButton.on('click.tools.cardform', $.proxy(this.toggle, this));
            this.$cancelButton.on('click.tools.cardform', $.proxy(function(e) {
                this.clear();
                this.unserialize();
                this.hide(e);
            }, this));
            var self = this;
            this.$element.on('success.tools.validate', function() { self.hide(false); });
        },
        toggle: function(e) {
            e.preventDefault();
            if (this.$element.hasClass('card-form-view')) {
                this.buffer = this.$element.serializeArray();
                this.$element.find('input,select,textarea').attr('readonly', false);
                this.$element.removeClass('card-form-view');
                this.$editButton.addClass('hide');
                this.$saveButton.removeClass('hide');
                this.$cancelButton.removeClass('hide');
                this.buttons.show();
            } else { this.hide(e); }
        },
        clear: function() {
            this.$element.find('.input-error').each($.proxy(function(i, s) {
                var name = $(s).attr('name');
                $('#' + name + '-error').removeClass('error').html('').hide();
                $(s).removeClass('input-error');
            }, this));
            $('.validate-error').removeClass('validate-error').html('').hide();
            $('.validate-message').remove();
        },
        unserialize: function() {
            var data = this.buffer;
            for (var key in this.buffer) { data[this.buffer[key].name] = this.buffer[key].value; }
            var val, $el, $form = this.$element.find('input,select,textarea');
            for (var i = 0, ii = $form.length; i < ii; i++) {
                $el = $($form[i]);
                val = data[$el.attr('name')];
                $el.val(val);
                if ($el.hasClass('select')) { $el.select('update'); }
            }
            this.buffer = {};
        },
        hide: function(e) {
            if (e !== false) { e.preventDefault(); }
            this.$element.find('input,select,textarea').attr('readonly', true);
            this.$element.addClass('card-form-view');
            this.$editButton.removeClass('hide');
            this.$saveButton.addClass('hide');
            this.$cancelButton.addClass('hide');
            this.buttons.hide();
        }
    });
})(jQuery);
(function($) {
    'use strict';
    var opts = { parent: false, target: false, classname: 'ch', highlight: 'highlight' };
    $.kube.plugin('check', '1.0', opts, {
        init: function() {
            this.$elements = $('.' + this.opts.classname);
            this.$target = $(this.opts.target);
            this.$element.on('click.tools.check', $.proxy(this.load, this));
            this.setter = (this.opts.target) ? this.$target.val().split(',') : [];
            this.$elements.each($.proxy(this.setOnStart, this));
        },
        load: function() {
            if (this.$element.prop('checked')) {
                this.$elements.prop('checked', true);
                if (this.opts.parent || this.opts.target) {
                    this.$elements.each($.proxy(function(i, s) {
                        var $s = $(s);
                        this.setHighlight($s);
                        this.setValue($s.val());
                    }, this));
                }
            } else {
                this.$elements.prop('checked', false);
                if (this.opts.parent) { this.$elements.each($.proxy(function(i, s) { this.removeHighlight($(s)); }, this)); }
                if (this.opts.target) { this.$target.val(''); }
            }
        },
        isCheckedAll: function() {
            var all = this.$elements.length;
            var checked = this.$elements.filter(':checked').length;
            return (all === checked);
        },
        setOnStart: function(i, el) {
            var $el = $(el);
            if (this.$element.prop('checked') || (this.setter && ($.inArray($el.val(), this.setter) !== -1))) {
                $el.prop('checked', true);
                this.setHighlight($el);
            }
            $el.on('click', $.proxy(function() {
                var checkedSize = this.$elements.filter(':checked').size();
                if ($el.prop('checked')) {
                    this.setValue($el.val());
                    this.setHighlight($el);
                } else {
                    this.removeValue($el.val());
                    this.removeHighlight($el);
                }
                var prop = (checkedSize !== this.$elements.size()) ? false : true;
                this.$element.prop('checked', prop);
            }, this));
        },
        setHighlight: function($el) {
            if (!this.opts.parent) {
                return;
            }
            this.setHighlightPrivate($el);
            if (this.isCheckedAll()) { this.setHighlightPrivate(this.$element); }
        },
        setHighlightPrivate: function($el) { $el.closest(this.opts.parent).addClass(this.opts.highlight); },
        removeHighlight: function($el) {
            if (!this.opts.parent) {
                return;
            }
            this.removeHighlightPrivate($el);
            if (!this.isCheckedAll()) { this.removeHighlightPrivate(this.$element); }
        },
        removeHighlightPrivate: function($el) { $el.closest(this.opts.parent).removeClass(this.opts.highlight); },
        setValue: function(value) {
            if (!this.opts.target) {
                return;
            }
            var str = this.$target.val();
            var arr = str.split(',');
            arr.push(value);
            arr = (str === '') ? [value] : arr;
            this.$target.val(arr.join(','));
        },
        removeValue: function(value) {
            if (!this.opts.target) {
                return;
            }
            var arr = this.$target.val().split(',');
            var index = arr.indexOf(value);
            arr.splice(index, 1);
            this.$target.val(arr.join(','));
        }
    });
})(jQuery);
(function($) {
    'use strict';
    var opts = { placeholder: false };
    $.kube.plugin('combobox', '1.0', opts, {
        init: function() {
            this.$sourceBox = $('<div class="combobox" />');
            this.$element.after(this.$sourceBox).hide();
            this.$sourceSelect = $('<span class="combobox-toggle" />');
            this.$sourceLayer = $('<ul class="combobox-list hide" />');
            this.$sourceResult = this.$element;
            this.$source = $('<input type="text" id="' + this.$element.attr('id') + '-input" class="' + this.$element.attr('class') + '" />');
            this.$sourceBox.append(this.$sourceResult);
            this.$sourceBox.append(this.$source);
            this.$sourceBox.append(this.$sourceSelect);
            this.$sourceBox.append(this.$sourceLayer);
            this.setPlaceholder();
            this.$element.find('option').each($.proxy(this.buildListItemsFromOptions, this));
            var key = this.$element.val();
            var value = this.$element.find('option:selected').text();
            this.setResult(key, value);
            this.$source.on('keyup.tools.combobox', $.proxy(this.clearSelected, this));
            this.$sourceSelect.on('click.tools.combobox', $.proxy(this.load, this));
        },
        load: function(e) {
            e.preventDefault();
            if (this.$sourceLayer.hasClass('open')) {
                this.close();
                return;
            }
            var value = this.$element.val();
            this.$sourceLayer.addClass('open').slideDown(300);
            var items = this.$sourceLayer.find('li').removeClass('active');
            this.setSelectedItem(items, value);
            $(document).on('click.tools.combobox', $.proxy(this.close, this));
            $(document).on('keydown.tools.combobox', $.proxy(function(e) {
                var key = e.which;
                var $el;
                var item;
                if (key === 38) {
                    e.preventDefault();
                    if (items.hasClass('active')) {
                        item = items.filter('li.active');
                        item.removeClass('active');
                        var prev = item.prev();
                        $el = (prev.size() !== 0) ? $el = prev : items.last();
                    } else { $el = items.last(); }
                    $el.addClass('active');
                    this.setScrollTop($el);
                } else if (key === 40) {
                    e.preventDefault();
                    if (items.hasClass('active')) {
                        item = items.filter('li.active');
                        item.removeClass('active');
                        var next = item.next();
                        $el = (next.size() !== 0) ? next : items.first();
                    } else { $el = items.first(); }
                    $el.addClass('active');
                    this.setScrollTop($el);
                } else if (key === 13) {
                    if (!items.hasClass('active')) {
                        return;
                    }
                    item = items.filter('li.active');
                    this.onItemClick(e, item);
                } else if (key === 27) { this.close(); }
            }, this));
        },
        clearSelected: function() {
            var value = this.$source.val();
            this.setResult(value, value);
            if (this.$source.val().length === 0) { this.$element.val(0); }
        },
        setSelectedItem: function(items, value) {
            var selectEl = items.filter('[rel=' + value + ']');
            if (selectEl.size() === 0) {
                selectEl = false;
                var sourceValue = this.$source.val();
                $.each(items, function(i, s) {
                    var $s = $(s);
                    if ($s.text() === sourceValue) { selectEl = $s; }
                });
                if (selectEl === false) {
                    return;
                }
            }
            selectEl.addClass('active');
            this.setScrollTop(selectEl);
        },
        setScrollTop: function($el) { this.$sourceLayer.scrollTop(this.$sourceLayer.scrollTop() + $el.position().top - 40); },
        buildListItemsFromOptions: function(i, s) {
            var $el = $(s);
            var val = $el.val();
            if (val === 0) {
                return;
            }
            var item = $('<li />');
            item.attr('rel', val).text($el.text());
            item.on('click', $.proxy(this.onItemClick, this));
            this.$sourceLayer.append(item);
        },
        update: function(value) {
            var text = this.$sourceLayer.find('li').filter('[rel=' + value + ']').text();
            var option = $('<option value="' + value + '" selected="selected">' + text + '</option>');
            this.$sourceResult.html(option);
            this.$source.val(text);
        },
        onItemClick: function(e, item) {
            e.preventDefault();
            var $el = $(item || e.target);
            var rel = $el.attr('rel');
            var text = $el.text();
            this.$source.val(text);
            this.$element.val(rel);
            this.close();
            this.setResult(rel, text);
            this.callback(this, 'select', { id: rel, value: text });
        },
        setResult: function(key, value) {
            var option = $('<option value="' + key + '" selected="selected">' + value + '</option>');
            this.$sourceResult.html(option);
        },
        setPlaceholder: function() {
            if (!this.opts.placeholder && !this.$element.attr('placeholder')) {
                return;
            }
            this.$source.attr('placeholder', (this.opts.placeholder) ? this.opts.placeholder : this.$element.attr('placeholder'));
        },
        close: function(e) {
            if (e && ($(e.target).hasClass('combobox-toggle') || $(e.target).closest('div.combobox').size() === 1)) {
                return;
            }
            this.$sourceLayer.removeClass('open').slideUp(150);
            $(document).off('.tools.combobox');
        }
    });
})(jQuery);
(function($) {
    'use strict';
    var opts = { caret: true, target: false, targetClose: false, height: false, width: false };
    $.kube.plugin('dropdown', '1.0', opts, {
        init: function() {
            this.$dropdown = $(this.opts.target);
            this.$dropdown.hide();
            if (this.opts.caret) {
                this.$caret = $('<b class="caret"></b>');
                this.$element.append(this.$caret);
                this.setCaretUp();
            }
            this.$element.on('click.tools.dropdown', $.proxy(this.toggle, this));
        },
        setCaretUp: function() {
            var height = this.$element.offset().top + this.$element.innerHeight() + this.$dropdown.innerHeight();
            if ($(document).height() > height) {
                return;
            }
            this.$caret.addClass('caret-up');
        },
        toggle: function(e) {
            e.preventDefault();
            return (this.$element.hasClass('dropdown-in')) ? this.hide() : this.show();
        },
        getPlacement: function(height) {
            return ($(document).height() < height) ? 'top' : 'bottom';
        },
        getOffset: function(position) {
            return (position === 'fixed') ? this.$element.position() : this.$element.offset();
        },
        getPosition: function() {
            return (this.$element.closest('.nav-fixed').size() !== 0) ? 'fixed' : 'absolute';
        },
        setPosition: function() {
            var width, height;
            if (this.isMobile()) {
                width = $(window).width();
                height = $(window).height();
                this.$dropdown.css({ position: 'fixed', width: width + 'px', 'min-height': height + 'px', top: 0, left: 0 });
                this.$close = $('<span />').html('&times;').addClass('dropdown-close').on('click.tools.dropdown', $.proxy(function() { this.hide(false); }, this));
                this.$dropdown.prepend(this.$close);
            } else {
                var position = this.getPosition();
                var pos = this.getOffset(position);
                var elementHeight = this.$element.innerHeight();
                var elementWidth = this.$element.innerWidth();
                height = this.$dropdown.innerHeight();
                width = this.$dropdown.innerWidth();
                var placement = this.getPlacement(pos.top + height + elementHeight);
                var leftFix = 0;
                if ($(window).width() < (pos.left + width)) { leftFix = (width - elementWidth); }
                var top, left = pos.left - leftFix;
                if (placement === 'bottom') {
                    if (this.opts.caret) { this.$caret.removeClass('caret-up'); }
                    top = pos.top + elementHeight;
                } else {
                    if (this.opts.caret) { this.$caret.addClass('caret-up'); }
                    top = pos.top - height;
                }
                this.$dropdown.css({ position: position, top: top + 'px', left: left + 'px' });
            }
        },
        show: function() {
            this.callback(this, 'open', this.$dropdown, this.$element);
            $('.dropdown-in').removeClass('dropdown-in');
            $('.dropdown').removeClass('dropdown-open').hide();
            if (this.opts.height) { this.$dropdown.css('min-height', this.opts.height + 'px'); }
            if (this.opts.width) { this.$dropdown.width(this.opts.width); }
            this.setPosition();
            this.$dropdown.addClass('dropdown-open').slideDown(400);
            this.$element.addClass('dropdown-in');
            this.$dropdown.on('mouseover.tools.dropdown', $.proxy(this.disableBodyScroll, this)).on('mouseout.tools.dropdown', $.proxy(this.enableBodyScroll, this));
            $(document).on('scroll.tools.dropdown', $.proxy(this.setPosition, this));
            $(window).on('resize.tools.dropdown', $.proxy(this.setPosition, this));
            $(document).on('click.tools.dropdown touchstart.tools.dropdown', $.proxy(this.hide, this));
            if (this.opts.targetClose) {
                $(this.opts.targetClose).on('click.tools.dropdown', $.proxy(function(e) {
                    e.preventDefault();
                    this.hide(false);
                }, this));
            }
            $(document).on('keydown.tools.dropdown', $.proxy(function(e) {
                if (e.which === 27) { this.hide(); }
            }, this));
            this.callback(this, 'opened', this.$dropdown, this.$element);
        },
        hide: function(e) {
            if (e) {
                e = e.originalEvent || e;
                var $target = $(e.target);
                if ($target.hasClass('caret') || $target.hasClass('dropdown-in') || $target.closest('.dropdown-open').size() !== 0) {
                    return;
                }
            }
            this.callback(this, 'close', this.$dropdown, this.$element);
            this.$dropdown.removeClass('dropdown-open').slideUp(200);
            this.$element.removeClass('dropdown-in');
            this.$dropdown.off('.tools.dropdown');
            $(document).off('.tools.dropdown');
            $(window).off('.tools.dropdown');
            if (this.$close) { this.$close.remove(); }
            this.callback(this, 'closed', this.$dropdown, this.$element);
        },
        isMobile: function() {
            return /(iPhone|iPod|BlackBerry|Android)/.test(navigator.userAgent);
        }
    });
})(jQuery);
(function($) {
    'use strict';
    var opts = { target: false };
    $.kube.listenClass('equals', 'equals');
    $.kube.plugin('equals', '1.0', opts, {
        init: function() {
            if (!this.opts.target) {
                return;
            }
            this.elements = this.$element.find(this.opts.target);
            this.equalize(false);
            $(window).on('resize', $.proxy(this.equalize, this));
        },
        equalize: function(resize) {
            if (resize !== false) { this.elements.css({ height: 'auto' }); }
            var mq = window.matchMedia("(max-width:767px)");
            if (mq.matches) {
                return;
            }
            var max = 0;
            var h;
            $.each(this.elements, function() {
                h = $(this).height();
                max = h > max ? h : max;
            });
            this.elements.height(max);
        }
    });
})(jQuery);
(function($) {
    'use strict';
    var opts = {};
    $.kube.plugin('fixednav', '1.0', opts, {
        init: function() {
            var mq = window.matchMedia("(max-width:767px)");
            if (mq.matches) {
                return;
            }
            this.navBoxOffsetTop = this.$element.offset().top;
            this.build();
            $(window).scroll($.proxy(this.build, this));
        },
        build: function() {
            if ($(window).scrollTop() > this.navBoxOffsetTop) {
                this.$element.addClass('nav-fixed');
                this.callback(this, 'fixed', this.$element);
            } else {
                this.$element.removeClass('nav-fixed');
                this.callback(this, 'unfixed', this.$element);
            }
        }
    });
})(jQuery);
(function($) {
    'use strict';
    var opts = { target: false };
    $.kube.plugin('layoutnav', '1.0', opts, {
        init: function() {
            this.$target = $(this.opts.target);
            this.$close = this.$target.find('.close');
            this.$element.on('click.tools.layoutnav', $.proxy(this.toggle, this));
        },
        toggle: function(e) {
            if (this.$target.hasClass('open')) { this.hide(e); } else { this.show(e); }
        },
        show: function(e) {
            e.preventDefault();
            this.$target.fadeIn(400).addClass('open');
            this.$close.on('click.tools.layoutnav', $.proxy(this.hide, this));
            $(document).on('keyup.tools.layoutnav', $.proxy(this.hideHandler, this));
            this.disableBodyScroll();
        },
        hideHandler: function(e) {
            if (e.which !== 27) {
                return;
            }
            this.hide(e);
        },
        hide: function(e) {
            e.preventDefault();
            this.$target.fadeOut(200).removeClass('open');
            this.$close.off('.tools.layoutnav');
            $(document).off('.tools.layoutnav');
            this.enableBodyScroll();
        }
    });
})(jQuery);
(function($) {
    'use strict';
    var opts = { url: false, target: false, min: 2, focusWidth: false, blurWidth: false, expand: false, params: false, appendForms: false, targetToggle: false, elementsToggle: false };
    $.kube.plugin('livesearch', '1.0', opts, {
        init: function() {
            this.$box = $('<span class="livesearch-box" />');
            if (this.$element.hasClass('input-big')) { this.$box.addClass('livesearch-big'); }
            if (this.$element.hasClass('input-small')) { this.$box.addClass('livesearch-small'); }
            this.$element.after(this.$box);
            this.$box.append(this.$element);
            this.timeout = null;
            this.$element.attr('autocomplete', 'off').attr('role', 'search');
            this.$element.off('keyup.tools.livesearch');
            this.$element.on('keyup.tools.livesearch', $.proxy(this.load, this));
            this.$icon = $('<span class="livesearch-icon" />');
            this.$box.append(this.$icon);
            this.$icon.on('click.tools.livesearch', $.proxy(function() { this.$element.focus(); }, this));
            this.$close = $('<span class="close" />').hide();
            this.$box.append(this.$close);
            this.$close.off('click.tools.livesearch');
            this.$close.on('click.tools.livesearch', $.proxy(function() {
                var data = '';
                data = this.getParams(data);
                data = this.appendForms(data);
                this.search(data, false);
                this.$element.val('');
                this.$close.hide();
                if (this.opts.expand) { this.$element.css('width', this.opts.blurWidth).blur(); } else { this.$element.focus(); }
            }, this));
            if (this.opts.focusWidth && this.opts.blurWidth) {
                var mq = window.matchMedia("(max-width:767px)");
                if (!mq.matches) {
                    this.opts.expand = true;
                    this.expand();
                }
            }
        },
        toggleClose: function(length) {
            return (length === 0) ? this.$close.hide() : this.$close.show();
        },
        load: function() {
            clearTimeout(this.timeout);
            this.timeout = setTimeout($.proxy(function() {
                var value = this.$element.val();
                var data = '';
                var results = false;
                if (value.length > this.opts.min) {
                    var name = 'q';
                    if (typeof this.$element.attr('name') !== 'undefined') { name = this.$element.attr('name'); }
                    data += '&' + name + '=' + value;
                    results = true;
                }
                data = this.getParams(data);
                data = this.appendForms(data);
                this.toggleClose(value.length);
                this.search(data, results);
            }, this), 300);
        },
        search: function(data, results) {
            if (results) {
                if (this.opts.targetToggle) { $(this.opts.target).show(); }
                if (this.opts.elementsToggle) { $(this.opts.elementsToggle).hide(); }
            } else {
                if (this.opts.targetToggle) { $(this.opts.target).hide(); }
                if (this.opts.elementsToggle) { $(this.opts.elementsToggle).show(); }
            }
            $.ajax({
                url: this.opts.url,
                type: 'post',
                data: data,
                success: $.proxy(function(result) {
                    $(this.opts.target).html(result);
                    this.callback(this, 'result', result);
                }, this)
            });
        },
        expand: function() {
            var self = this;
            this.$element.css({ 'min-width': 0, 'width': this.opts.blurWidth }).on('focus', function() { $(this).removeClass('input-blur').addClass('input-focus').animate({ width: self.opts.focusWidth }); }).on('blur', function() { $(this).removeClass('input-focus').addClass('input-blur').animate({ width: self.opts.blurWidth }); });
        }
    });
})(jQuery);
(function($) {
    'use strict';
    var opts = { rel: false, classname: 'tab', activeClassname: 'active' };
    $.kube.plugin('livetabs', '1.0', opts, {
        init: function() {
            this.tabs = $('.' + this.opts.classname);
            this.$element.html('');
            if (this.tabs.size() > 1) {
                this.$target = $('<ul />');
                this.$element.append(this.$target);
            } else {
                return;
            }
            $.each(this.tabs, $.proxy(function(i, s) {
                var li = $('<li />');
                var a = $('<a href="#" />').text($(s).data('title'));
                var rel = $(s).attr('rel');
                this.opts.rel = (typeof rel !== 'undefined');
                a.attr('rel', (this.opts.rel) ? rel : i);
                a.on('click', $.proxy(this.show, this));
                li.append(a);
                this.$target.append(li);
            }, this));
            this.hash = top.location.hash.replace('#', '');
            if (this.hash === '') {
                this.$target.find('a').first().closest('li').addClass(this.opts.activeClassname);
                var first = this.tabs.first();
                first.show();
                if (this.opts.rel) { top.location.hash = first.attr('rel'); }
            } else {
                this.$target.find('a').filter('[rel=' + this.hash + ']').closest('li').addClass(this.opts.activeClassname);
                this.tabs.hide().filter('[rel=' + this.hash + ']').show();
            }
        },
        show: function(e) {
            e.preventDefault();
            var index = $(e.target).attr('rel');
            if (this.opts.rel) {
                top.location.hash = index;
                this.tabs.hide().filter('[rel=' + index + ']').show();
                this.$target.find('li').removeClass(this.opts.activeClassname);
                this.$target.find('a').filter('[rel=' + index + ']').closest('li').addClass(this.opts.activeClassname);
            } else {
                this.tabs.hide().eq(index).show();
                this.$target.find('li').removeClass(this.opts.activeClassname);
                this.$target.find('a').eq(index).closest('li').addClass(this.opts.activeClassname);
            }
        }
    });
})(jQuery);
(function($) {
    'use strict';
    var opts = { url: false, params: false, targetHide: false, targetShow: false, targetHtml: false, targetValue: false };
    $.kube.plugin('magicquery', '1.0', opts, {
        init: function() { this.$element.on('click.tools.magicquery', $.proxy(this.query, this)); },
        query: function(e) {
            e.preventDefault();
            $.ajax({
                url: this.opts.url,
                type: 'post',
                data: this.getParams(),
                success: $.proxy(function(data) {
                    if (this.opts.targetValue) { $(this.opts.targetValue).val(data); }
                    if (this.opts.targetHtml) { $(this.opts.targetHtml).html(data); }
                    if (this.opts.targetHide) { $(this.opts.targetHide).fadeOut(250); }
                    if (this.opts.targetShow) { $(this.opts.targetShow).removeClass('hide').fadeIn(400); }
                }, this)
            });
        }
    });
})(jQuery);
(function($) {
    'use strict';
    $.message = function(content, options) {
        return new Message(content, options);
    };
    var Message = function(content, options) {
        this.opts = $.extend({ delay: 10, theme: 'primary', line: false }, options);
        if (content.indexOf('#') === -1) { this.init(content); } else {
            this.$message = $(content);
            this.open();
        }
    };
    Message.prototype = {
        init: function(content) {
            this.hideAll();
            this.$message = $('<div class="message" />').addClass('message-' + this.opts.theme).addClass('message-generated').text(content).hide();
            if (this.opts.line) { this.$message.addClass('message-line'); }
            $('body').append(this.$message);
            this.events(this.$message);
        },
        open: function() {
            this.hideAll();
            this.$message.fadeIn(500);
            this.events(this.$message);
        },
        events: function($el) {
            $el.fadeIn(500).on('click.tools.message', $.proxy(this.close, this));
            $(document).on('keyup.tools.message', $.proxy(this.closeHandler, this));
            if (this.opts.delay) { setTimeout($.proxy(this.close, this), this.opts.delay * 1000); }
        },
        closeHandler: function(e) {
            return (e.which === 27) ? this.close() : true;
        },
        close: function() { this.takeOut(this.$message, 'fadeOut'); },
        hideAll: function() { this.takeOut($('.message'), 'hide'); },
        takeOut: function($el, func) {
            var duration = (func === 'fadeOut') ? 250 : 0;
            $(document).off('keyup.tools.message');
            $el.off('click.tools.message')[func](duration, function() {
                var $el = $(this);
                if ($el.hasClass('message-generated')) { $el.remove(); }
            });
        }
    };
})(jQuery);
(function($) {
    'use strict';
    var opts = { content: false, title: '', width: '500px', blur: false, show: false };
    $.modalwindow = function(options) {
        var element = document.createElement('span');
        options.show = true;
        $(element).modal(options);
    };
    $.kube.plugin('modal', '1.0', opts, {
        init: function() {
            if (this.opts.show) { this.load(); } else { this.$element.on('click.tools.modal', $.proxy(this.load, this)); }
        },
        load: function(e) {
            if (typeof e !== 'undefined') { e.preventDefault(); }
            this.build();
            this.enableEvents();
            this.setTitle();
            this.setDraggable();
            this.setContent();
        },
        build: function() {
            this.buildOverlay();
            this.$modalBox = $('<div class="modal-box" />').hide();
            this.$modal = $('<div class="modal" />');
            this.$modalHeader = $('<header />');
            this.$modalClose = $('<span class="modal-close" />').html('&times;');
            this.$modalBody = $('<div class="modal-section" />');
            this.$modalFooter = $('<footer />');
            this.$modal.append(this.$modalHeader);
            this.$modal.append(this.$modalClose);
            this.$modal.append(this.$modalBody);
            this.$modal.append(this.$modalFooter);
            this.$modalBox.append(this.$modal);
            this.$modalBox.appendTo(document.body);
        },
        buildOverlay: function() {
            this.$modalOverlay = $('<div id="modal-overlay">').hide();
            $('body').prepend(this.$modalOverlay);
            if (this.opts.blur) {
                this.blurredElements = $('body').children('div,section,header,article,pre,aside,table').not('.modal,.modal-box,#modal-overlay');
                this.blurredElements.addClass('modal-blur');
            }
        },
        show: function() {
            this.callback(this, 'open', this.$modal);
            if (this.isMobile()) { this.showOnMobile(); } else { this.showOnDesktop(); }
            this.$modalOverlay.show();
            this.$modalBox.fadeIn(600);
            this.setButtonsWidth();
            if (!this.isMobile()) {
                setTimeout($.proxy(this.showOnDesktop, this), 0);
                $(window).on('resize.tools.modal', $.proxy(this.resize, this));
            }
            this.callback(this, 'opened', this.$modal);
            $(document).off('focusin.modal');
            this.$modal.find('input[type=text],input[type=url],input[type=email]').on('keydown.redactor-modal', $.proxy(this.setEnter, this));
        },
        showOnDesktop: function() {
            var height = this.$modal.outerHeight();
            var windowHeight = $(window).height();
            var windowWidth = $(window).width();
            if (this.opts.width.match(/%$/)) { this.$modal.css('width', this.opts.width); } else if (parseInt(this.opts.width) > windowWidth) { this.$modal.css('width', '96%'); } else { this.$modal.css('width', this.opts.width); }
            if (height > windowHeight) { this.$modal.css('margin-top', '20px'); } else { this.$modal.css('margin-top', (windowHeight / 2 - height / 2) + 'px'); }
            this.disableBodyScroll();
        },
        showOnMobile: function() { this.$modal.css({ width: '96%', marginTop: '2%' }); },
        setEnter: function(e) {
            if (e.which !== 13) {
                return;
            }
            e.preventDefault();
            this.$modal.find('button.btn-modal-action').click();
        },
        resize: function() {
            return (this.isMobile()) ? this.showOnMobile() : this.showOnDesktop();
        },
        title: function(title) { this.$modalHeader.text(title); },
        content: function(content) {
            this.$modalBody.html(content);
            this.show();
        },
        setTitle: function() { this.$modalHeader.text(this.opts.title); },
        setContent: function() {
            if (typeof this.opts.content === 'object' || this.opts.content.search('#') === 0) {
                this.type = 'html';
                this.cached = $(this.opts.content).html();
                this.$modalBody.html(this.cached);
                this.show();
            } else {
                $.ajax({
                    url: this.opts.content,
                    cache: false,
                    type: 'post',
                    success: $.proxy(function(data) {
                        this.$modalBody.html(data);
                        this.show();
                    }, this)
                });
            }
        },
        setDraggable: function() {
            if (typeof $.fn.draggable === 'undefined') {
                return;
            }
            this.$modal.draggable({ handle: this.$modalHeader });
            this.$modalHeader.css('cursor', 'move');
        },
        createCancelButton: function(label) {
            label = (typeof label === 'undefined') ? 'Cancel' : label;
            var button = $('<button>').addClass('modal-close-btn').text(label);
            button.on('click', $.proxy(this.close, this));
            this.$modalFooter.append(button);
        },
        createDeleteButton: function(label) {
            label = (typeof label === 'undefined') ? 'Delete' : label;
            return this.createButton(label, 'black', 'btn-modal-delete');
        },
        createActionButton: function(label) {
            label = (typeof label === 'undefined') ? 'Ok' : label;
            return this.createButton(label, 'primary', 'btn-modal-action');
        },
        createButton: function(label, type, className) {
            var button = $('<button>').attr('type', type).addClass(className).text(label);
            this.$modalFooter.append(button);
            return button;
        },
        setButtonsWidth: function() {
            var buttons = this.$modalFooter.find('button');
            var buttonsSize = buttons.size();
            if (buttonsSize === 0) {
                return;
            }
            buttons.css('width', (100 / buttonsSize) + '%');
        },
        enableEvents: function() {
            this.$modalClose.on('click.tools.modal', $.proxy(this.close, this));
            $(document).on('keyup.tools.modal', $.proxy(this.closeHandler, this));
            this.$modalBox.on('click.tools.modal', $.proxy(this.close, this));
        },
        disableEvents: function() {
            this.$modalClose.off('.tools.modal');
            $(document).off('.tools.modal');
            this.$modalBox.off('.tools.modal');
            $(window).off('.tools.modal');
        },
        closeHandler: function(e) {
            if (e.which !== 27) {
                return;
            }
            this.close();
        },
        close: function(e) {
            if (e) {
                if (!$(e.target).hasClass('modal-close-btn') && e.target !== this.$modalClose[0] && e.target !== this.$modalBox[0]) {
                    return;
                }
                e.preventDefault();
            }
            if (!this.$modalBox) {
                return;
            }
            this.callback(this, 'close');
            this.disableEvents();
            this.$modalBox.fadeOut(400, $.proxy(function() {
                this.$modalOverlay.fadeOut('fast', function() { $(this).remove(); });
                this.$modalBox.remove();
                this.enableBodyScroll();
                if (this.opts.blur && typeof this.blurredElements !== 'undefined') { this.blurredElements.removeClass('modal-blur'); }
                if (this.type === 'html') { $(this.opts.content).html(this.cached); }
                this.callback(this, 'closed');
            }, this));
        },
        isMobile: function() {
            var mq = window.matchMedia("(max-width:767px)");
            return (mq.matches) ? true : false;
        }
    });
})(jQuery);
(function($) {
    'use strict';
    var opts = { start: false, target: false, direction: 'left', push: false, closeOutside: true, pushBodyClass: 'push-body', navOpenClass: 'nav-out-open', breakpoint: '767px' };
    $.kube.plugin('outnav', '1.0', opts, {
        init: function() {
            if (!this.opts.target) {
                return;
            }
            if (this.opts.start) { this.start(); } else {
                this.build();
                $(window).resize($.proxy(this.build, this));
            }
            this.$element.addClass('nav-out-element').on('click.tools.outnav', $.proxy(this.toggle, this));
        },
        start: function() {
            $('body').addClass(this.opts.pushBodyClass);
            $(this.opts.target).addClass('nav-out').addClass('nav-out nav-out-' + this.opts.direction);
            this.$element.show();
        },
        build: function() {
            var mq = window.matchMedia("(max-width:" + this.opts.breakpoint + ")");
            if (mq.matches) { this.start(); } else {
                $('body').removeClass(this.opts.pushBodyClass).removeClass(this.opts.pushBodyClass + '-to-left ' + this.opts.pushBodyClass + '-to-right');
                $(this.opts.target).removeClass('nav-out').removeClass('nav-out nav-out-' + this.opts.direction).removeClass(this.opts.navOpenClass);
                this.$element.hide();
            }
        },
        toggle: function(e) {
            e.preventDefault();
            if ($(this.opts.target).hasClass(this.opts.navOpenClass)) { this.hide(false); } else { this.show(); }
        },
        show: function() {
            if (this.opts.push) {
                var direction = (this.opts.direction === 'left') ? 'right' : 'left';
                $('body').toggleClass(this.opts.pushBodyClass + '-to-' + direction);
            }
            $(this.opts.target).addClass(this.opts.navOpenClass);
            if (this.opts.closeOutside) { $(document).on('click.tools.outnav', $.proxy(this.hide, this)); }
            this.disableBodyScroll();
            this.callback(this, 'opened');
        },
        hide: function(e) {
            if (e !== false && $(e.target).closest('.nav-out-element').size() !== 0) {
                return;
            }
            $('body').removeClass(this.opts.pushBodyClass + '-to-left ' + this.opts.pushBodyClass + '-to-right');
            $('.nav-out').removeClass(this.opts.navOpenClass);
            $(document).off('click.tools.outnav');
            this.enableBodyScroll();
            this.callback(this, 'closed');
            return true;
        }
    });
})(jQuery);
(function($) {
    'use strict';
    $.progress = {
        selector: '#tools-progress',
        show: function() {
            if ($(this.selector).length === 0) {
                var id = this.selector.replace(/^#/, '');
                var $stripes = $('<span />');
                var $progress = $('<div id="' + id + '"></div>').append($stripes).hide();
                $(document.body).append($progress);
            }
            $(this.selector).fadeIn();
        },
        update: function(value) {
            this.show();
            $(this.selector).find('span').css('width', value + '%');
        },
        hide: function() { $(this.selector).fadeOut(1500); }
    };
})(jQuery);
(function($) {
    'use strict';
    var opts = { width: false };
    $.kube.listenClass('select', 'select');
    $.kube.plugin('select', '1.0', opts, {
        init: function() {
            if (this.$element.hasClass('tools-selected') || this.$element[0].tagName !== 'SELECT') {
                return;
            }
            this.$element.addClass('tools-selected');
            this.$element.wrap('<span class="tools-select">');
            var $wrapper = this.$element.parent();
            $wrapper.append('<span class="select-trigger">');
            $.each(this.$element[0].classList, $.proxy(function(i, s) {
                if (typeof s === 'undefined') {
                    return;
                } else if (s.match(/^width\-/i)) {
                    $wrapper.addClass(s);
                    this.$element.removeClass(s);
                } else if (s.match(/^select\-/i)) { $wrapper.addClass(s + '-box'); }
            }, this));
            this.$element.addClass('width-12');
            if (this.opts.width) { $wrapper.width(this.opts.width); }
            this.$trigger = $wrapper.find('.select-trigger');
            this.update();
            if (this.$element.attr('disabled')) {
                this.$trigger.addClass('disabled');
                return;
            }
            this.$element.on('change', $.proxy(this.update, this));
            this.$element.on('focus', $.proxy(this.focus, this));
            this.$element.on('blur', $.proxy(this.blur, this));
        },
        update: function() {
            var triggerHtml = this.$element.find(':selected').text();
            this.$trigger.text(triggerHtml);
        },
        focus: function() { this.$trigger.addClass('focus'); },
        blur: function() { this.$trigger.removeClass('focus'); }
    });
})(jQuery);
(function($) {
    'use strict';
    var opts = { target: false };
    $.kube.plugin('slug', '1.0', opts, {
        init: function() {
            var $target = $(this.opts.target);
            this.$element.on('keyup.tools.slug', $.proxy(function() {
                var address = this.$element.val();
                address = address.toLowerCase();
                address = address.replace(/[\s_]/gi, '-');
                address = address.replace(/[\.,\/#!$%\^&\*;:"“”'{}=_`~()]/g, '');
                $target.val(address);
            }, this));
            $target.on('keyup.tools.slug,keydown.tools.slug', $.proxy(function() { this.$element.off('keyup.tools.slug'); }, this));
        }
    });
})(jQuery);
(function($) {
    'use strict';
    var opts = {};
    $.kube.plugin('tabkey', '1.0', opts, {
        init: function() {
            this.$element.on('keydown.tools.tabkey', function(e) {
                if (e.keyCode !== 9) {
                    return true;
                }
                var $el = $(this);
                var start = $el.get(0).selectionStart;
                $el.val($el.val().substring(0, start) + "\t" + $el.val().substring($el.get(0).selectionEnd));
                $el.get(0).selectionStart = $el.get(0).selectionEnd = start + 1;
                return false;
            });
        }
    });
})(jQuery);
(function($) {
    'use strict';
    var opts = { equals: false, active: false };
    $.kube.plugin('tabs', '1.0', opts, {
        init: function() {
            this.links = this.$element.find('a');
            this.tabs = [];
            this.links.each($.proxy(this.load, this));
            this.setEquals();
            this.callback(this, 'init');
        },
        load: function(i, el) {
            var $el = $(el);
            var hash = $el.attr('href');
            $el.attr('rel', hash);
            this.tabs.push($(hash));
            if (!$el.hasClass('active')) { $(hash).hide(); }
            this.readLocationHash(hash);
            if (this.opts.active !== false && this.opts.active === hash) { this.show(hash); }
            $el.on('click.tools.tabs', $.proxy(this.toggle, this));
        },
        toggle: function(e) {
            e.preventDefault();
            var hash = $(e.target).attr('rel');
            this.show(hash);
        },
        readLocationHash: function(hash) {
            if (top.location.hash === '' || top.location.hash !== hash) {
                return;
            }
            this.opts.active = top.location.hash;
        },
        setActive: function(hash) {
            this.activeHash = hash;
            this.activeTab = $('[rel=' + hash + ']');
            this.links.removeClass('active');
            this.activeTab.addClass('active');
        },
        getActiveHash: function() {
            return this.activeHash;
        },
        getActiveTab: function() {
            return this.activeTab;
        },
        show: function(hash) {
            this.hideAll();
            $(hash).show();
            this.setActive(hash);
            this.callback(this, 'opened', $('[rel=' + hash + ']'), hash);
        },
        hideAll: function() {
            $.each(this.tabs, function() { $(this).hide(); });
            this.links.removeClass('active');
        },
        setEquals: function() {
            if (!this.opts.equals) {
                return;
            }
            var minHeight = this.getMaxHeight() + 'px';
            $.each(this.tabs, function() { $(this).css('min-height', minHeight); });
        },
        getMaxHeight: function() {
            var max = 0;
            $.each(this.tabs, function() {
                var h = $(this).height();
                max = h > max ? h : max;
            });
            return max;
        }
    });
})(jQuery);
(function($) {
    'use strict';
    var opts = { min: '10px', max: '100px', compressor: 1 };
    $.kube.listenClass('textfit', 'textfit');
    $.kube.plugin('textfit', '1.0', opts, {
        init: function() {
            var size = Math.max(Math.min(this.$element.width() / (this.opts.compressor * 10), parseFloat(this.opts.max)), parseFloat(this.opts.min));
            this.$element.css('font-size', size).css('line-height', size * 1.2 + 'px');
        }
    });
})(jQuery);
(function($) {
    'use strict';
    var opts = { replace: false, target: false };
    $.kube.plugin('toggleme', '1.0', opts, {
        init: function() { this.$element.attr('rel', this.$element.text()).on('click.tools.togglme', $.proxy(this.toggleElement, this)); },
        toggleElement: function(e) {
            e.preventDefault();
            var $target;
            if (this.opts.target === 'next') { $target = this.$element.next(); } else if (this.opts.target === 'parent+next') { $target = this.$element.parent().next(); } else { $target = $(this.opts.target); }
            var text = this.$element.attr('rel');
            if ($target.css('display') === 'none') {
                $target.slideDown(500);
                setTimeout($.proxy(function() { this.$element.text((this.opts.replace) ? this.opts.replace : text); }, this), 300);
            } else {
                $target.slideUp(250);
                setTimeout($.proxy(function() { this.$element.text(text); }, this), 150);
            }
        }
    });
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
    var opts = { url: false, placeholder: 'Drop file here or ', name: false, padding: 80, value: false, html: false, params: false, appendForms: false };
    $.kube.plugin('upload', '1.0', opts, {
        init: function() {
            this.$droparea = $('<div class="tools-droparea" />');
            this.$placeholdler = $('<div class="tools-droparea-placeholder" />').text(this.opts.placeholder);
            this.$droparea.append(this.$placeholdler);
            this.$element.after(this.$droparea);
            this.$placeholdler.append(this.$element);
            this.$droparea.off('.tools.upload');
            this.$element.off('change.tools.upload');
            this.$droparea.on('dragover.tools.upload', $.proxy(this.onDrag, this));
            this.$droparea.on('dragleave.tools.upload', $.proxy(this.onDragLeave, this));
            if (this.opts.padding) {
                this.opts.padding = this.opts.padding - parseInt(this.$droparea.css('border-width') - 1) * 2;
                this.$droparea.css({ 'padding-top': this.opts.padding + 'px', 'padding-bottom': this.opts.padding + 'px' });
            }
            this.$element.on('change.tools.upload', $.proxy(function(e) {
                e = e.originalEvent || e;
                this.traverseFile(this.$element[0].files[0], e);
            }, this));
            this.$droparea.on('drop.tools.upload', $.proxy(function(e) {
                e.preventDefault();
                this.$droparea.removeClass('drag-hover').addClass('drag-drop');
                this.onDrop(e);
            }, this));
        },
        onDrop: function(e) {
            e = e.originalEvent || e;
            var files = e.dataTransfer.files;
            this.clearErrors();
            this.traverseFile(files[0], e);
        },
        clearErrors: function() {
            var name = this.$element.attr('name');
            $('#' + name + '-error').removeClass('error').text('').hide();
        },
        traverseFile: function(file) {
            var formData = !!window.FormData ? new FormData() : null;
            if (window.FormData) {
                var name = (this.opts.name === false) ? this.$element.attr('name') : this.opts.name;
                formData.append(name, file);
                formData = this.appendFormsData(formData);
                formData = this.getParamsData(formData);
            }
            if ($.progress) { $.progress.show(); }
            this.callback(this, 'start');
            this.sendData(formData);
        },
        sendData: function(formData) {
            var xhr = new XMLHttpRequest();
            xhr.open('POST', this.opts.url);
            xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
            xhr.onreadystatechange = $.proxy(function() {
                if (xhr.readyState === 4) {
                    var data = xhr.responseText;
                    data = data.replace(/^\[/, '');
                    data = data.replace(/\]$/, '');
                    var json = (typeof data === 'string' ? $.parseJSON(data) : data);
                    if ($.progress) { $.progress.hide(); }
                    if (typeof json.type !== 'undefined' && json.type === 'error') {
                        var name = this.$element.attr('name');
                        $.each(json.errors, $.proxy(function(i, text) {
                            $('#' + name + '-error').addClass('error').text(text).show();
                        }, this));
                        this.callback(this, 'error', json.errors);
                    } else {
                        this.clearErrors();
                        var args;
                        if (this.opts.value !== false) {
                            args = this.opts.value.split(':');
                            if (typeof json[args[0]] !== 'undefined') { $(args[1]).val(json[args[0]]); }
                        }
                        if (this.opts.html !== false) {
                            args = this.opts.html.split(':');
                            if (typeof json[args[0]] !== 'undefined') { $(args[1]).text(json[args[0]]); }
                        }
                        this.callback(this, 'success', json);
                    }
                    this.$droparea.removeClass('drag-drop');
                    this.$element.val('');
                }
            }, this);
            xhr.send(formData);
        },
        onDrag: function(e) {
            e.preventDefault();
            this.$droparea.addClass('drag-hover');
        },
        onDragLeave: function(e) {
            e.preventDefault();
            this.$droparea.removeClass('drag-hover');
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
                if (typeof name !== 'undefined') { data.push(name + '=' + encodeURIComponent($el.val())); }
            });
            this.callback(this, 'send');
            $.ajax({ url: this.opts.url, type: 'post', data: data.join("&"), success: $.proxy(this.parse, this) });
        },
        send: function() {
            this.disabled();
            $('.CodeMirror').each(function(i, el) { el.CodeMirror.save(); });
            this.callback(this, 'send');
            if (!this.opts.send) {
                return;
            }
            $.ajax({ url: this.opts.url, type: 'post', data: this.$element.serialize(), success: $.proxy(this.parse, this) });
        },
        parse: function(jsonString) {
            this.enabled();
            this.clear();
            var obj = {};
            if (jsonString !== '') {
                jsonString = jsonString.replace(/^\[/, '');
                jsonString = jsonString.replace(/\]$/, '');
                try { obj = $.parseJSON(jsonString); } catch (e) { obj = jsonString; }
            }
            if (obj.type === 'error') {
                $.each(obj.errors, $.proxy(function(name, text) {
                    var $form = this.form();
                    var $el = $($form.find('[name=' + name + ']'));
                    var redactor = $el.data('redactor');
                    if (typeof redactor !== 'undefined') { redactor.core.box().addClass(this.opts.errorClassName); }
                    $el.addClass(this.opts.errorClassName);
                    if (text !== '') { this.showError($el, name, text, redactor); }
                }, this));
                this.callback(this, 'error', obj.errors);
            } else {
                if (obj.type === 'html') { $.each(obj.data, $.proxy(function(i, s) { $(i).html(this.stripslashes(this.urldecode(s))); }, this)); } else if (obj.type === 'command') { $.each(obj.data, $.proxy(function(i, s) { $(s)[i](); }, this)); } else if (obj.type === 'location') { top.location.href = obj.data; } else if (obj.type === 'message') { this.showMessage(obj); }
                this.callback(this, 'success', obj);
            }
        },
        showMessage: function(obj) {
            var text = '';
            if ($.isArray(obj.data)) {
                text = '<ul>';
                var len = obj.data.length;
                for (var i = 0; i < len; i++) { text += '<li>' + obj.data[i] + '</li>'; }
                text += '</ul>';
            } else { text = obj.data; }
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
            if (this.opts.progress && $.progress) { $.progress.show(); }
            var $form = this.form();
            $form.find('button').attr('disabled', true);
        },
        enabled: function() {
            if (this.opts.progress && $.progress) { $.progress.hide(); }
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

