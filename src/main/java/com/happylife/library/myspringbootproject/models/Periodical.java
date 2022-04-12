package com.happylife.library.myspringbootproject.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = Periodical.NamedQuery_GetAllPeriodicals,
                procedureName = "GET_ALL_PERIODICALS",
                resultClasses = Periodical.class,
                parameters = {
                        @StoredProcedureParameter(type = void.class, mode = ParameterMode.REF_CURSOR),
                }
        ),
        @NamedStoredProcedureQuery(
                name = Periodical.NamedQuery_SubscribeTo,
                procedureName = "SUBSCRIBE_TO",
                parameters = {
                        @StoredProcedureParameter(type = Long.class, mode = ParameterMode.IN),
                        @StoredProcedureParameter(type = String.class, mode = ParameterMode.IN)
                }

        ),
        @NamedStoredProcedureQuery(
                name = Periodical.NamedQuery_UnSubscribeFrom,
                procedureName = "UNSUBSCRIBE_FROM",
                parameters = {
                        @StoredProcedureParameter(type = Long.class, mode = ParameterMode.IN),
                        @StoredProcedureParameter(type = String.class, mode = ParameterMode.IN)
                }

        ),

})

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "periodical")
public class Periodical {

    public static final String NamedQuery_GetAllPeriodicals = "getAllPeriodicals";

    public static final String NamedQuery_SubscribeTo = "subscribeTo";

    public static final String NamedQuery_UnSubscribeFrom = "unsubscribeFrom";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "available")
    private String available;

    @Column(name = "type")
    private String type;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @OneToMany(mappedBy = "periodical", cascade = CascadeType.ALL)
    private Set<Orders> orders;
}
