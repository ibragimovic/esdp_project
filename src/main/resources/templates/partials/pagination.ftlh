<div>
        <nav aria-label="...">
                <ul class="pagination">
                <#if hasPrev>
                        <li class="page-item">
                                <span class="page-link"><a id="loadPrev" href="${prevPageLink!}">Previous</a></span>
                        </li>
                <#else>
                        <li class="page-item disabled">
                                <span class="page-link" id="loadPrev">Previous</span>
                        </li>
                </#if>
                <#if hasNext>
                        <li class="page-item">
                                <a class="page-link" id="loadNext" href="${nextPageLink!}" data-default-page-size="${defaultPageSize!5}">Next</a>
                        </li>
                <#else>
                        <li class="page-item disabled">
                                <span class="page-link" id="loadPrev">Next</span>
                        </li>
                </#if>
                </ul>
        </nav>
</div>