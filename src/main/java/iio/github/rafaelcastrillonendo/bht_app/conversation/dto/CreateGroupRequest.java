package iio.github.rafaelcastrillonendo.bht_app.conversation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

public record CreateGroupRequest(
        @NotBlank
        @Length(min = 5, max = 50)
        String name,
        @NotEmpty
        @Size(max = 100)
        Set<@NotNull Long> memberIds
) {
}
