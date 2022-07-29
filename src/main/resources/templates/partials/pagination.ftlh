<#import "/spring.ftl" as spring>
<div>
    <#if hasPrev>
        <a id="loadPrev" href="${prevPageLink!''}"
           style="padding-left: 30px">Прерыдущая страница </a>
    <#else>
        <span id="loadPrev" style="padding-left: 30px">Прерыдущая страница</span>
    </#if>

    <#if hasNext>
        <a id="loadNext" href="${nextPageLink!''}" data-default-page-size="${defaultPageSize!5}"
           style="padding-left: 30px">Следующая страница </a>
    <#else>
        <span style="padding-left: 30px">Следующая страница</span>
    </#if>
</div>