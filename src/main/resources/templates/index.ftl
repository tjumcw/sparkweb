<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="/css/global.css">
    <link rel="stylesheet" href="/css/index.css">
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.13.1/themes/smoothness/jquery-ui.css">
    <script src="/js/jquery.min.js"></script>
    <script src="/js/jquery-ui.min.js"></script>
    <script src="/js/index.js"></script>

    <script>
        function toggleLogShowState(isShow) {
            if (!isShow) {
                $("#logHistoryList").hide()
            } else {
                $("#logHistoryList").show()
            }
        }
    </script>
</head>

<body>
<div class="header">
    <h1 class="title wrapper">
        Insight BundesTag
    </h1>
    <h2 class="subtitle">subtitle</h2>
</div>

<div class="content wrapper">
    <#--  第一行查询speaker和speech相关  -->
    <div class="dataContainer">
        <div class="queryBox container">
            <#--      查询的表单      -->
            <form id="querySpeaker" class="speakerBox ">
                <div class="queryLine">
                    <input type="text" id="speakerInput" placeholder="enter id or name">
                    <button type="submit" class="speakerBtn">Query</button>
                </div>
                <div id="speakerRadioGroup">
                    <input type="radio" id="optionName" name="options" value="name" checked>
                    <label for="optionName">name</label>
                    <input type="radio" id="optionId" name="options" value="id">
                    <label for="optionId">id</label>
                    <input type="radio" id="optionFraction" name="options" value="fraction">
                    <label for="optionFraction">fraction</label>
                    <input type="radio" id="optionParty" name="options" value="party">
                    <label for="optionParty">party</label>
                </div>
            </form>
            <#--      渲染的列表      -->
            <div id="speakerList">
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>FirstName</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="speechBox container">
            <#--      日期选择组件      -->
            <div class="dateLine">
                <div class="dataSelect">
                    <input id="startDatePicker" placeholder="请选择开始日期.."/>
                    <span style="width: 16px"></span>
                    <input id="endDatePicker" placeholder="请选择结束日期.."/>
                </div>
                <button id="dateBtn">Select start time and end time, and then click to query</button>
            </div>
            <#--      speech渲染列表      -->
            <div id="speechList">
                <table>
                    <thead>
                    <tr>
                        <th>Speaker</th>
                        <th>Text Length</th>
                        <th>text Link</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <#--  第二行关于历史日志相关  -->
    <div class="logContainer container">
        <div class="buttonGroup">
            <button onclick="toggleLogShowState(true)">Show Log</button>
            <button onclick="toggleLogShowState(false)" class="hideBtn">Hide Log</button>
        </div>
        <div id="logHistoryList">
            <table>
                <thead>
                <tr>
                    <th>Query Type</th>
                    <th>Query Value</th>
                    <th>Query Time</th>
                    <th>Recovery Data</th>
                </tr>
                </thead>
                <tbody>
                <#if logHistoryList?has_content>
                    <#list logHistoryList as item>
                        <tr style="display: flex; width: 100%">
                            <td style="width: calc(25%)">${item.queryType}</td>
                            <td style="width: calc(25%)">${item.queryValue}</td>
                            <td style="width: calc(25%)">${item.queryTime}</td>
                            <td style="width: calc(25%)"><button>recovery data</button></td>
                        </tr>
                    </#list>
                </#if>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>

</html>