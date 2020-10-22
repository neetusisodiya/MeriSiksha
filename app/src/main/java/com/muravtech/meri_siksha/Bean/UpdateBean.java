package com.muravtech.meri_siksha.Bean;

import java.io.Serializable;
import java.util.List;

public class UpdateBean implements Serializable {
    /**
     * path : https://merishiksha.in/upload/gallery/
     * data : [{"id":1,"school_id":"12","filename":[{"file_0":"15938445905f00236e8a717.png","file_1":"15938445905f00236e8a810.png"}],"link":"","type":"image","created_at":"2020-07-04 08:36:30"}]
     * status : true
     * message : Data get succesfully
     */

    private String path;
    private String status;
    private String message;
    private List<DataBean> data;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * id : 1
         * school_id : 12
         * filename : [{"file_0":"15938445905f00236e8a717.png","file_1":"15938445905f00236e8a810.png"}]
         * link :
         * type : image
         * created_at : 2020-07-04 08:36:30
         */

        private int id;
        private String school_id;
        private String link;
        private String type;
        private String created_at;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        private String title;
        private List<FilenameBean> filename;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public List<FilenameBean> getFilename() {
            return filename;
        }

        public void setFilename(List<FilenameBean> filename) {
            this.filename = filename;
        }

        public static class FilenameBean implements Serializable {
            /**
             * file : 15938445905f00236e8a717.png

             */

            private String file;

            public String getFile() {
                return file;
            }

            public void setFile(String file_0) {
                this.file = file_0;
            }


        }
    }
}