package scg.fusion.library.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class BookId implements Serializable {

    private static final long serialVersionUID = -8980963893098739888L;

    private String title;
    private String author;

}
