package com.github.zigcat.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.NullNode;
import com.github.zigcat.exceptions.IncorrectRequestException;
import com.github.zigcat.exceptions.RedirectException;
import com.github.zigcat.ormlite.models.Form;
import com.github.zigcat.ormlite.models.Role;
import com.github.zigcat.ormlite.models.School;
import com.github.zigcat.ormlite.models.User;
import com.github.zigcat.ormlite.services.Security;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;

public class UserDeserializer extends StdDeserializer<User> {
    protected UserDeserializer(Class<?> vc) {
        super(vc);
    }

    protected UserDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected UserDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    public UserDeserializer(){
        super(User.class);
    }

    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        int id = node.get("id").asInt();
        String name = node.get("name").asText();
        String surname = node.get("surname").asText();
        String email = node.get("email").asText();
        if(!Security.isValidEmail(email)){
            throw new IncorrectRequestException("email isn't valid(400)");
        }
        String password = BCrypt.hashpw(node.get("password").asText(), BCrypt.gensalt(12));
        Role role = Role.valueOf(node.get("role").asText());
        String birthday = node.get("birthday").asText();
        if(!Security.isCorrectDate(birthday)){
            throw new IncorrectRequestException("date is incorrect(400)");
        }
        if((node.get("school") instanceof NullNode || node.get("school") == null)
            && (node.get("class") instanceof NullNode || node.get("class") == null)){
            return new User(id, name, surname, email, password, role, birthday);
        }
        int schoolId = node.get("school").asInt();
        int formId = node.get("class").asInt();
        School school;
        Form form;
        int accepted = node.get("isAccepted").asInt();
        boolean isAccepted;
        if(accepted == 1){
            isAccepted = true;
        } else if(accepted == 0){
            isAccepted = false;
        } else {
            throw new IncorrectRequestException("isAccepted isn't valid(400)");
        }
        try {
            school = School.schoolControl.getService().getById(School.schoolControl.getDao(), schoolId);
            form = Form.formControl.getService().getById(Form.formControl.getDao(), formId);
            return new User(id, name, surname, email, password, role, birthday, school, form, isAccepted);
        } catch (SQLException e) {
            throw new RedirectException("SQLException");
        }
    }
}
