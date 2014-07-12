package domain;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExclusionFilter {

    private List<String> excludedCategories;

    public ExclusionFilter(String excludedCategories) {
        if(!excludedCategories.contains(",")){
            this.excludedCategories = new ArrayList<>();
            this.excludedCategories.add(excludedCategories);
        } else {
            String[] ex = excludedCategories.split(",");
                this.excludedCategories = new ArrayList(Arrays.asList(ex));
        }
    }

    public List<String> getExclusions(){
        return excludedCategories;
    }
}
