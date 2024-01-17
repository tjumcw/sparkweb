$(document).ready(function () {

    /**
     * message对象的构造方法
     * @constructor
     */
    function MessageBox() {
        this.container = $('<div>', { id: 'message-container' });

        // 初始化消息容器
        this.initContainer = function () {
            this.container.css({
                position: 'fixed',
                top: '50%',
                left: '50%',
                backgroundColor: '#f8d7da',
                color: '#721c24',
                transform: 'translate(-50%, -50%)',
                padding: '10px',
                border: '1px solid #f5c6cb',
                borderRadius: '5px',
                display: 'none'
            });

            $('body').append(this.container);
        };

        // 显示消息
        this.show = function (message, type) {
            // 设置不同类型的样式
            if (type === 'error') {
                this.container.css({
                    'background-color': '#f8d7da',
                    'color': '#721c24',
                    'border-color': '#f5c6cb'
                });
            } else if (type === 'success') {
                this.container.css({
                    'background-color': '#d4edda',
                    'color': '#155724',
                    'border-color': '#c3e6cb'
                });
            }

            // 显示消息
            this.container.text(message).fadeIn().delay(2000).fadeOut();
        };

        // 显示错误消息
        this.showError = function (message) {
            this.show(message, 'error');
        };

        // 显示成功消息
        this.showSuccess = function (message) {
            this.show(message, 'success');
        };

        // 调用初始化
        this.initContainer();
    }

    /**
     * 全局挂载message对象
     */
    if (!window.messageBox) {
        window.messageBox = new MessageBox();
    }

    /**
     * 注册speaker查询表单提交事件
     */
    $("#querySpeaker").on('submit', function (e) {
        e.preventDefault();

        if (!$('#speakerInput').val()) {
            messageBox.showError("query is empty");
            return;
        }
        // 获取用户选择的搜索类型
        let searchType;
        const labels = $("#speakerRadioGroup").find("input[type='radio']");
        labels.each(function () {
            if ($(this).prop("checked")) {
                searchType = $(this).val();
                searchType = searchType == "id" ? "_id" : searchType;
            }
        })

        // 获取表单数据，发送 Ajax 请求
        const formData = {
            queryValue: $('#speakerInput').val(),
            field: searchType
        };
        const logData = {
            queryType: "speaker-" + searchType,
            queryValue: formData,
            queryTime: getCurrentDateStr(new Date()),
        };
        $.ajax({
            url: 'http://localhost:4567/api/query/speaker',  // 后端接收请求的 URL
            type: 'POST',
            contentType: 'application/json', // 设置请求头的 Content-Type
            dataType: 'json', // 期望的响应数据类型
            data: JSON.stringify(formData),
            success: function(response) {
                if (response.success) {
                    // 在成功后的操作，根据需要处理后端返回的响应
                    renderSpeakerList(response.data)
                } else {
                    messageBox.showError(response.message)
                }
            },
            error: function(error) {
                // 处理错误
                messageBox.showError(error)
            }
        });
        logTriggerHandler(logData);
    });

    /**
     * 初始化日期选择器
     */
    function initDatePicker() {
        // 设置日期选择器的语言为英文
        $.datepicker.setDefaults($.datepicker.regional['en']);
        // 创建两个日期选择器
        $("#startDatePicker").datepicker({
            onSelect: function (dateText, inst) {
                // 设置最小日期为选择的日期
                $("#endDatePicker").datepicker("option", "minDate", dateText);
            },
            yearRange: '2021:2023', // 设置可选择的年份范围
            changeYear: true, // 允许手动输入年份
            changeMonth: true, // 显示月份下拉列表
            defaultDate: '01/01/2022'
        });
        $("#endDatePicker").datepicker({
            onSelect: function (dateText, inst) {
                // 设置最大日期为选择的日期
                $("#startDatePicker").datepicker("option", "maxDate", dateText);
            },
            yearRange: '2021:2023', // 设置可选择的年份范围
            changeYear: true, // 允许手动输入年份
            changeMonth: true, // 显示月份下拉列表
            defaultDate: '01/01/2022'
        })
    }
    initDatePicker();

    /**
     * 日期查询按钮事件方法
     */
    $("#dateBtn").on('click', function () {
        if ($("#startDatePicker").val() && $("#endDatePicker").val()) {
            // 执行 AJAX 请求
            querySpeeches();
        } else {
            messageBox.showError("please select start time and end time");
        }
    })

    /**
     * 渲染speaker列表
     * @param data
     */
    function renderSpeakerList(data) {
        const tbody = document.querySelector('#speakerList tbody');
        // 清空 tbody 中的现有内容
        tbody.innerHTML = '';

        // 遍历数据，动态生成表格行
        data.forEach(item => {
            const row = document.createElement('tr');
            row.style.display = "flex";
            const filterItem = {
                id: item["_id"],
                name: item.name,
                firstName: item.firstName
            }
            // 根据数据的键名，生成对应的单元格
            for (const key in filterItem) {
                const cell = document.createElement('td');
                cell.style.width = "calc(100%/3)"
                cell.textContent = filterItem[key];
                row.appendChild(cell);
            }
            // 将生成的行添加到 tbody 中
            tbody.appendChild(row);
        })
    }

    /**
     * 查询speech信息，成功则渲染列表
     */
    function querySpeeches() {
        const startTime = new Date($("#startDatePicker").val());
        const endTime = new Date($("#endDatePicker").val());
        $.ajax({
            url: 'http://localhost:4567/api/query/speech',  // 后端接收请求的 URL
            type: 'POST',
            contentType: 'application/json', // 设置请求头的 Content-Type
            dataType: 'json', // 期望的响应数据类型
            data: JSON.stringify({
                startTime: startTime.getTime(),
                endTime: endTime.getTime(),
            }),
            success: function(response) {
                if (response.success) {
                    // 在成功后的操作，根据需要处理后端返回的响应
                    renderSpeechList(response.data);
                } else {
                    messageBox.showError(response.message);
                }
            },
            error: function(error) {
                // 处理错误
                messageBox.showError(error);
            }
        });
        const logData = {
            queryType: "speech",
            queryValue: getCurrentDateStr(startTime) + "/" + getCurrentDateStr(endTime),
            queryTime: getCurrentDateStr(new Date()),
        }
        logTriggerHandler(logData);
    }

    /**
     * 渲染speech列表
     * @param data
     */
    function renderSpeechList(data) {
        const tbody = document.querySelector('#speechList tbody');
        // 清空 tbody 中的现有内容
        tbody.innerHTML = '';

        // 遍历数据，动态生成表格行
        data.forEach(item => {
            const row = document.createElement('tr');
            row.style.display = "flex";
            const filterItem = {
                speaker: item.speaker,
                length: item.length,
                id: item["_id"],
            }
            // 根据数据的键名，生成对应的单元格
            for (const key in filterItem) {
                const cell = document.createElement('td');
                cell.style.width = "calc(100%/3)"
                if (key == "id") {
                    const link = document.createElement('a');
                    link.href = '/speech/' + filterItem[key]; // 替换为你的链接
                    link.textContent = filterItem[key];
                    link.style.cssText = 'color: #990000; font-weight: 700; text-decoration: underline;';
                    cell.appendChild(link);
                } else {
                    cell.textContent = filterItem[key];
                }
                row.appendChild(cell);
            }
            // 将生成的行添加到 tbody 中
            tbody.appendChild(row);
        })
    }

    /**
     * 日志触发处理方法
     */
    function logTriggerHandler(logData) {
        $.ajax({
            url: 'http://localhost:4567/api/log/add',  // 后端接收请求的 URL
            type: 'POST',
            contentType: 'application/json', // 设置请求头的 Content-Type
            dataType: 'json', // 期望的响应数据类型
            data: JSON.stringify(logData),
            success: function(response) {
                if (response.success) {
                    // 在成功后的操作，根据需要处理后端返回的响应
                    logHistoryList.unshift({
                        ...logData,
                        queryValue: JSON.stringify(logData.queryValue),
                    });
                    renderLogHistory();
                } else {
                    messageBox.showError(response.message);
                }
            },
            error: function(error) {
                // 处理错误
                messageBox.showError(error);
            }
        });
    }

    /**
     * 初始化日志历史列表数据
     * @type {*[]}
     */
    const logHistoryList = []
    function initLogHistoryList() {
        const rows = $("#logHistoryList tbody tr");
        rows.each(function () {
            let tds = $(this).find('td:lt(3)');
            const data = {
                queryType: tds[0].innerText,
                queryValue: tds[1].innerText,
                queryTime: tds[2].innerText,
            }
            console.log(data)
            logHistoryList.push(data)
            let btn = this.querySelector('button');
            btn.addEventListener('click', function () {
                recoveryHandler(data);
            });
        });
    }

    initLogHistoryList();

    /**
     * 渲染日志历史
     */
    function renderLogHistory() {
        const tbody = document.querySelector("#logHistoryList tbody");
        tbody.innerHTML = "";

        logHistoryList.forEach(item => {
            const row = document.createElement('tr');
            row.style.display = "flex";
            // 根据数据的键名，生成对应的单元格
            for (const key in item) {
                const cell = document.createElement('td');
                cell.style.width = "calc(100%/4)"
                cell.textContent = item[key];
                row.appendChild(cell);
            }
            const cell = document.createElement('td');
            const recoveryBtn = document.createElement('button');
            recoveryBtn.addEventListener('click', function () {
                recoveryHandler(item);
            });
            cell.style.width = "calc(100%/4)"
            recoveryBtn.textContent = "recovery data";
            cell.appendChild(recoveryBtn);
            row.appendChild(cell);
            // 将生成的行添加到 tbody 中
            tbody.appendChild(row);
        })
    }

    /**
     * 按照历史搜索记录回显表单并回到搜索位置
     * @param data
     */
    function recoveryHandler(data) {
        if (data.queryType == "speech") {
            const [startTime, endTime] = data.queryValue.split("/");
            $("#startDatePicker").val(startTime);
            $("#endDatePicker").val(endTime);
            scrollToTarget($("#startDatePicker"));
        } else if (data.queryType.includes("speaker")) {
            const queryValue = JSON.parse(data.queryValue);
            const type = data.queryType.split("-")[1]
            $('#speakerInput').val(queryValue.queryValue);
            const labels = $("#speakerRadioGroup").find("input[type='radio']");
            labels.filter(`[value="${type}"]`).prop('checked', true);
            scrollToTarget($('#speakerInput'));
        }
    }

    /**
     * 平滑滚动到目标位置
     * @param element
     */
    function scrollToTarget(element) {
        const target = $(element).offset().top;
        $('html, body').animate({
            scrollTop: target
        }, 500); // 500 是滚动的时间，可以根据需要调整
    }

    /**
     * 获取格式化时间
     * @param date 传入的事件对象
     * @returns {string}
     */
    function getCurrentDateStr(date) {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0'); // Month is zero-based
        const day = String(date.getDate()).padStart(2, '0');

        const formattedDate = `${year}-${month}-${day}`;
        return formattedDate;
    }
});

