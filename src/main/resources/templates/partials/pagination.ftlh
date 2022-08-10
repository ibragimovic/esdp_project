<#import "/spring.ftl" as spring>
<div>

    <nav aria-label="...">
        <ul class="pagination">
            <#if hasPrev>
                <li class="page-item ">
                    <a class="page-link" href="${prevPageLink!''}">Предыдущая</a>
                </li>
            <#else>
                <li class="page-item disabled">
                    <span class="page-link">Предыдущая</span>
                </li>
            </#if>
                <#if hasNext>
            <li class="page-item">
                    <a class="page-link" id="loadNext" href="${nextPageLink!''}"
                       data-default-page-size="${defaultPageSize!5}">Следующая</a>
            </li>
                <#else>
            <li class="page-item disabled">
                    <span class="page-link disabled">Следующая</span>
            </li>
                </#if>
        </ul>
    </nav>

</div>



