<#import "/spring.ftl" as spring>
<div>
    <#if hasPrev>
        <a id="loadPrev" href="${prevPageLink!''}"
           style="padding-left: 30px"><img src="/images/left-arrow.png"></a>
    <#else>
    <#--        <span id="loadPrev" style="padding-left: 30px"><img src="/images/left-arrow.png"></span>-->
    </#if>

    <#if hasNext>
        <a id="loadNext" href="${nextPageLink!''}" data-default-page-size="${defaultPageSize!5}"
           class="ms-auto"><img src="/images/right-arrow.png"></a>
    <#else>
    <#--        <span class="ms-auto"><img src="/images/right-arrow.png"></span>-->
    </#if>
</div>



