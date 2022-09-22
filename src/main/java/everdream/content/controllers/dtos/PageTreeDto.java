package everdream.content.controllers.dtos;

public class PageTreeDto {
    private Long id;
    private Long bookId;
    private Long currentPageId;
    private Long parentPageId;

    PageTreeDto(Long id, Long bookId, Long currentPageId, Long parentPageId) {
        this.id = id;
        this.bookId = bookId;
        this.currentPageId = currentPageId;
        this.parentPageId = parentPageId;
    }

    public static PageTreeDtoBuilder builder() {
        return new PageTreeDtoBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public Long getBookId() {
        return this.bookId;
    }

    public Long getCurrentPageId() {
        return this.currentPageId;
    }

    public Long getParentPageId() {
        return this.parentPageId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public void setCurrentPageId(Long currentPageId) {
        this.currentPageId = currentPageId;
    }

    public void setParentPageId(Long parentPageId) {
        this.parentPageId = parentPageId;
    }

    public static class PageTreeDtoBuilder {
        private Long id;
        private Long bookId;
        private Long currentPageId;
        private Long parentPageId;

        PageTreeDtoBuilder() {
        }

        public PageTreeDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PageTreeDtoBuilder bookId(Long bookId) {
            this.bookId = bookId;
            return this;
        }

        public PageTreeDtoBuilder currentPageId(Long currentPageId) {
            this.currentPageId = currentPageId;
            return this;
        }

        public PageTreeDtoBuilder parentPageId(Long parentPageId) {
            this.parentPageId = parentPageId;
            return this;
        }

        public PageTreeDto build() {
            return new PageTreeDto(id, bookId, currentPageId, parentPageId);
        }

        public String toString() {
            return "PageTreeDto.PageTreeDtoBuilder(id=" + this.id + ", bookId=" + this.bookId + ", currentPageId=" + this.currentPageId + ", parentPageId=" + this.parentPageId + ")";
        }
    }
}

