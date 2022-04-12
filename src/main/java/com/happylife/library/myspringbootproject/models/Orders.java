package com.happylife.library.myspringbootproject.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = Orders.NamedQuery_GetStatus,
                procedureName = "GET_STATUS",

                parameters = {
                        @StoredProcedureParameter(type = String.class, mode = ParameterMode.IN),
                }
        ),
})

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="orders")
public class Orders {

    public static final String NamedQuery_GetStatus = "getStatus";

    public static final String NamedQuery_TimeRemained = "getTimeRemained";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="order_date")
    private String orderDate;

    @Column(name="order_status")
    private String orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "periodical_id")
    private Periodical periodical;
}
