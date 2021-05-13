package system.handling.language.placeholder;


import system.database.table.DefaultEntries;
import system.database.table.Tables;
import system.handling.language.Language;
import system.tools.skills.TimeManager;

import java.util.ArrayList;
import java.util.List;

public class LanguagePlaceholder{

    public enum DefaultLanguagePlaceholder implements DefaultEntries{


        CUSTOMER("3E07D0C213E2E9E6F16D3A2A33B99A05>>AjFFZbds1jiNHx", "customer");

        private final String placeholderId;
        private final String name;

        DefaultLanguagePlaceholder(final String contentId, final String name){
            this.name = name;
            this.placeholderId = contentId;
        }

        public String getPlaceholderId(){
            return this.placeholderId;
        }

        public String getName(){
            return this.name;
        }

        @Override
        public Tables getTable(){
            return Tables.IVY_LANGUAGE_PLACEHOLDER;
        }

        @Override
        public List<String[]> getValues(){
            List<String[]> forReturn = new ArrayList<>();

            for(DefaultLanguagePlaceholder defaultPlaceholder : DefaultLanguagePlaceholder.values()){
                String[] values = {
                        defaultPlaceholder.getPlaceholderId(), defaultPlaceholder.getName(), new TimeManager(System.currentTimeMillis()).getDateFormat("YYYY-MM-dd HH:mm:ss"),
                };
                forReturn.add(values);
            }

            return forReturn;
        }
    }

    public enum DefaultLanguagePlaceholderContent implements DefaultEntries{

        CUSTOMER_EN("32310A01F27F629B48F621203D03BDA4>>dgwdueZO49lRBU", DefaultLanguagePlaceholder.CUSTOMER, Language.DefaultLanguages.ENGLISH, "Customer"),
        CUSTOMER_DE("6F1418A7936712DDA97F8A5DAFC6DA34>>oUGkaE7mnE0XaM", DefaultLanguagePlaceholder.CUSTOMER, Language.DefaultLanguages.GERMAN, "Kunde");

        private final String contentId;
        private final DefaultLanguagePlaceholder placeholder;
        private final Language.DefaultLanguages defaultLanguage;
        private final String value;

        DefaultLanguagePlaceholderContent(final String contentId, final DefaultLanguagePlaceholder placeholder, final Language.DefaultLanguages defaultLanguage, final String content){
            this.contentId = contentId;
            this.placeholder = placeholder;
            this.defaultLanguage = defaultLanguage;
            this.value = content;
        }

        public String getValue(){
            return this.value;
        }

        public String getContentId(){
            return this.contentId;
        }

        public DefaultLanguagePlaceholder getContentPlaceholder(){
            return this.placeholder;
        }

        public Language.DefaultLanguages getDefaultLanguage(){
            return this.defaultLanguage;
        }

        @Override
        public Tables getTable(){
            return Tables.IVY_LANGUAGE_PLACEHOLDER_CONTENT;
        }

        @Override
        public List<String[]> getValues(){
            List<String[]> forReturn = new ArrayList<>();

            for(DefaultLanguagePlaceholderContent defaultPlaceholderContent : DefaultLanguagePlaceholderContent.values()){
                String[] values = {
                        defaultPlaceholderContent.getContentId(), defaultPlaceholderContent.getContentPlaceholder().getPlaceholderId(), defaultPlaceholderContent.getDefaultLanguage().getLanguageId(), defaultPlaceholderContent.getValue(), new TimeManager(System.currentTimeMillis()).getDateFormat("YYYY-MM-dd HH:mm:ss")
                };
                forReturn.add(values);
            }

            return forReturn;
        }
    }


}
