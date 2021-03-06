package com.mykolalehkyi.travelapp.dao;

import com.mykolalehkyi.travelapp.model.Hotel;
import com.mykolalehkyi.travelapp.model.Order;
import com.mykolalehkyi.travelapp.model.Room;

import com.mykolalehkyi.travelapp.model.User;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class HotelDetailsDaoImp implements HotelDetailsDao{
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Hotel findHotelByName(String name) {
        return sessionFactory.getCurrentSession().get(Hotel.class, name);
    }

    @Override
    public Room findHotelRoomByName(String hotelName, String roomName) {
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Room.class);
            criteria.add(Restrictions.eq("name", roomName));
            criteria.add(Restrictions.eq("hotel", findHotelByName(hotelName)));
        return (Room) criteria.uniqueResult();
    }

    @Override
    public void save(Hotel hotel) {
        sessionFactory.getCurrentSession().save(hotel);
    }

    @Override
    public void save(Room room) {
        sessionFactory.getCurrentSession().save(room);
    }

    @Override
    public void save(Order order) {
        sessionFactory.getCurrentSession().save(order);
    }

    @Override
    public List<Hotel> loadAllHotels() {
        return sessionFactory.getCurrentSession().createQuery("from Hotel",Hotel.class).list();
    }

    @Override
    public List<Room> loadAllHotelRooms(String hotelName) {
        Query<Room> query = sessionFactory.getCurrentSession().createQuery("from Room where hotel_name = :hotel", Room.class);
        query.setString("hotel",hotelName);
        return query.list();

    }

    @Override
    public List<Order> loadAllUserOrders(User user) {
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Order.class);
        criteria.add(Restrictions.eq("user",user));
        return criteria.list();
    }

    @Override
    public List<String> selectDistinctCountries() {
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Hotel.class);
        criteria.setProjection(Projections.distinct(Projections.property("country")));
        List<String> result = criteria.list();
        return result;
    }

    @Override
    public List<String> selectNamesOfHotels() {
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Hotel.class);
        criteria.setProjection(Projections.property("name"));
        return criteria.list();
    }

    @Override
    public List<Hotel> selectHotelsByNameAndByCountryAndStars(String hotelName, String country, Integer stars) {
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Hotel.class);
        if (hotelName != null && !hotelName.isEmpty())
        criteria.add(Restrictions.ilike("name", "%"+hotelName+"%"));
        if (country != null && !country.isEmpty())
        criteria.add(Restrictions.eq("country", country));
        if (stars != null)
            criteria.add(Restrictions.eq("stars", stars));
        return criteria.list();
    }

    @Override
    public Boolean checkOrderOverlapping(Order order, Room room) {
        Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Order.class);
        criteria.add(
            Restrictions.and(
                    Restrictions.not(
                            Restrictions.or(
                                    Restrictions.lt("dateEnd",order.getDateBegin()),
                                    Restrictions.gt("dateBegin",order.getDateEnd())
                            )
                    ),
                    Restrictions.eq("room",room)
            )
        );
        return criteria.list().size()==0;
    }
}
