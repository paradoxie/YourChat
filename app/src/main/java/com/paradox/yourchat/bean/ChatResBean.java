package com.paradox.yourchat.bean;

import java.util.List;

public class ChatResBean {

    /**
     * id : chatcmpl-6psn3mdzXNEvQTaUOO1YwceSJcHLy
     * object : chat.completion
     * created : 1677823361
     * model : gpt-3.5-turbo-0301
     * usage : {"prompt_tokens":9,"completion_tokens":12,"total_tokens":21}
     * choices : [{"message":{"role":"assistant","content":"\n\nHello there! How can I assist you today?"},"finish_reason":"stop","index":0}]
     */

    private String id;
    private String object;
    private int created;
    private String model;
    private UsageBean usage;
    private ErrorBean error;
    private List<ChoicesBean> choices;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public UsageBean getUsage() {
        return usage;
    }

    public void setUsage(UsageBean usage) {
        this.usage = usage;
    }

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public List<ChoicesBean> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoicesBean> choices) {
        this.choices = choices;
    }

    public static class UsageBean {
        /**
         * prompt_tokens : 9
         * completion_tokens : 12
         * total_tokens : 21
         */

        private int prompt_tokens;
        private int completion_tokens;
        private int total_tokens;

        public int getPrompt_tokens() {
            return prompt_tokens;
        }

        public void setPrompt_tokens(int prompt_tokens) {
            this.prompt_tokens = prompt_tokens;
        }

        public int getCompletion_tokens() {
            return completion_tokens;
        }

        public void setCompletion_tokens(int completion_tokens) {
            this.completion_tokens = completion_tokens;
        }

        public int getTotal_tokens() {
            return total_tokens;
        }

        public void setTotal_tokens(int total_tokens) {
            this.total_tokens = total_tokens;
        }
    }

    public static class ChoicesBean {
        /**
         * message : {"role":"assistant","content":"\n\nHello there! How can I assist you today?"}
         * finish_reason : stop
         * index : 0
         */

        private MessageBean message;
        private String finish_reason;
        private int index;

        public MessageBean getMessage() {
            return message;
        }

        public void setMessage(MessageBean message) {
            this.message = message;
        }

        public String getFinish_reason() {
            return finish_reason;
        }

        public void setFinish_reason(String finish_reason) {
            this.finish_reason = finish_reason;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public static class MessageBean {
            /**
             * role : assistant
             * content :

             Hello there! How can I assist you today?
             */

            private String role;
            private String content;

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }

    public static class ErrorBean {
        private String message;
        private String code;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
